/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.processing;

/**
 *
 * @author tirth_parikh
 */
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Document_reader {

    public static final String[] SPECIAL = new String[]{"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "{", "]", "}", "\\\\", "|", ";", ":", "'", "\"", ",", "<", ".", ">", "/", "?", "~", "`"};
    public static final char[] SPECIAL_CHARS = new char[]{'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '[', '{', ']', '}', '|', ';', ':', '\'', ',', '<', '.', '>', '/', '?', '~', '`', '±', '°', '“', '®', '?'};

    public static boolean isSpecial(char val) {
        return isSpecial(String.valueOf(val));
    }

    /**
     * @param val
     * @return {@link Boolean}
     */
    public static boolean isSpecial(String val) {
        for (String tmp : SPECIAL) {
            if (val.equals(tmp)) {
                return true;
            }
        }
        return false;
    }

    public File selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.showOpenDialog(fileChooser);
        return new File(fileChooser.getSelectedFile().getAbsolutePath());
    }

    public void printpath(File file) {
        System.out.println("The path of the file you selected is " + file.getPath());
    }

    // This method reads a file, creates tokens (i.e single word) and print them
    // Currently it only excepts .txt file 
//    public void tokenizeDocument(File file) throws FileNotFoundException, IOException {
//        
//        //String to store the file extension
//        
//        String ext = FilenameUtils.getExtension(file.getName());
//        Vector token = new Vector();//could have used ArrayList as recommended by javadocs but I was used to this 
//        if ("txt".equals(ext)) {
//            Scanner s = new Scanner(file);
//            s.useDelimiter(Pattern.compile("\\s|\\.|,"));
//
//            int i = 0;
//            while (s.hasNext()) {
//                token.add(i, s.next());
//                i++;
//            }
//
//            for (int j = 0; j < i; j++) {
//                System.out.println(token.elementAt(j));
//            }
//
//            System.out.printf("\nThe total number of tokens are :%d ", i);
//        } else if ("pdf".equals(ext)) {
//            try (PDDocument document = PDDocument.load(file)) {
//                PDFTextStripper textstripper = new PDFTextStripper();
//                String str = textstripper.getText(document);
//                // String[] ss = str.split("\\s\\ss\\ssss\\sss");
//                int i = 0;
//               // for(i=0;i<ss.length;i++)
//                //   token.add(i,ss[i]);
//                StringTokenizer st = new StringTokenizer(str);
//                while (st.hasMoreTokens()) {
//                    token.add(i++, st.nextToken());
//                }
//                for (int j = 0; j < i; j++) {
//                    System.out.println(token.elementAt(j));
//                }
//
//                System.out.printf("\nThe total number of tokens are :%d ", i);
//                document.close();
//            }
//        } else {
//            System.out.println("Please upload only .txt files for now.\n Sorry for the limitations to the file types, We are updating ourselves");
//        }
//    }
    public List<String> process(String input) throws BusinessException {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        char[] arr = input.toCharArray();
        for (int i = 0; i < arr.length; i++) {

            char prior = (i - 1 > 0) ? arr[i - 1] : ' ';
            char current = arr[i];
            char next = (i + 1 < arr.length) ? arr[i + 1] : ' ';

			// extract acronyms
            // this will actually extract acronyms of any length
            // once it detects this pattern a.b.c 
            // it's a greedy lexer that breaks at ' '
            if (Character.isLetter(current) && '.' == next) {

				// Pattern-1	= U.S.A 	(5 chars)
                // Pattern-2	= U.S.A. 	(6 chars)
                if (i + 5 < input.length()) {

                    // Pattern-1
                    if (Character.isLetter(arr[i])
                            && '.' == arr[i + 1]
                            && Character.isLetter(arr[i + 2])
                            && '.' == arr[i + 3]
                            && Character.isLetter(arr[i + 4])) {

                        for (; i < arr.length && arr[i] != ' '; i++) {
                            sb.append(arr[i]);
                        }

                        // check for Pattern-2 (trailing '.')
                        if (i + 1 < input.length()
                                && '.' == arr[i + 1]) {
                            sb.append(arr[i++]);
                        }

                        addToken(tokens, sb);
                        sb = new StringBuilder();
                        continue;
                    }
                }
            }

            if ('w' == current && '/' == next) {
                sb.append(current);
                sb.append(next);
                addToken(tokens, sb);
                sb = new StringBuilder();
                i += 1;
                continue;
            }

            // extract URLs
            if ('h' == current && 't' == next) {
                if (i + 7 < input.length()
                        && "http://".equals(input.substring(i, i + 7))) {

                    for (; i < arr.length && arr[i] != ' '; i++) {
                        sb.append(arr[i]);
                    }

                    addToken(tokens, sb);
                    sb = new StringBuilder();
                    continue;
                }
            }

			// extract windows drive letter paths
            // c:/ or c:\
            if (Character.isLetter(current) && ':' == next) {
                if (i + 2 < input.length()
                        && (arr[i + 2] == '\\'
                        || arr[i + 2] == '/')) {
                    sb.append(current);
                    sb.append(next);
                    sb.append(arr[i + 2]);
                    i += 2;
                    continue;
                }
            }

			// keep numbers together when separated by a period
            // "4.0" should not be tokenized as { "4", ".", "0" }
            if (Character.isDigit(current) && '.' == next) {
                if (i + 2 < input.length()
                        && Character.isDigit(arr[i + 2])) {
                    sb.append(current);
                    sb.append(next);
                    sb.append(arr[i + 2]);
                    i += 2;
                    continue;
                }
            }

			// keep alpha characters separated by hyphens together
            // "b-node" should not be tokenized as { "b", "-", "node" }
            if (Character.isLetter(current) && '-' == next) {
                if (i + 2 < input.length()
                        && Character.isLetter(arr[i + 2])) {
                    sb.append(current);
                    sb.append(next);
                    sb.append(arr[i + 2]);
                    i += 2;
                    continue;
                }
            }

			// TODO: need a greedy look-ahead to 
            // avoid splitting this into multiple tokens 
            // "redbook@vnet.ibm.com" currently is 
            // tokenized as { "redbook@vnet", ".", "ibm", ".", "com" }
            // need to greedily lex all tokens up to the space
            // once the space is found, see if the last 4 chars are '.com' 
            // if so, then take the entire segment as a single token
            // don't separate tokens concatenated with an underscore
            // eg. "ws_srv01" is a single token, not { "ws", "_", "srv01" }
            if (Character.isLetter(current) && '_' == next) {
                if (i + 2 < input.length()
                        && Character.isLetter(arr[i + 2])) {
                    sb.append(current);
                    sb.append(next);
                    i++;
                    continue;
                }
            }

            // extract twitter channels
            if (('#' == current
                    || '@' == current)
                    && ' ' != next
                    && !Document_reader.isSpecial(next)) {
                sb.append(current);
                continue;
            }

            // keep tokens like tcp/ip and os/2 and system/z together
            if (' ' != current && '/' == next) {
                sb.append(current);
                sb.append(next);
                i++;
                continue;
            }

            if (' ' == current) {
                addToken(tokens, sb);
                sb = new StringBuilder();
                continue;
            }

			// don't tokenize on <word>'s or <words>'
            // but do tokenize on '<words>
            if ('\'' == current) {
                if (' ' == prior) {
                    addToken(tokens, "'");
                } else {
                    sb.append(current);
                }

                continue;
            }

            if (Document_reader.isSpecial(current)) {
                addToken(tokens, sb);
                addToken(tokens, String.valueOf(current));
                sb = new StringBuilder();
                continue;
            }

            sb.append(current);
        }

        if (0 != sb.length()) {
            addToken(tokens, sb);
        }

        return tokens;
    }

    protected void addToken(List<String> tokens, String text) {
        if (!text.isEmpty()) {
            tokens.add(text);
        }
    }

    protected void addToken(List<String> tokens, StringBuilder buffer) {
        if (null != buffer && 0 != buffer.length()) {
            addToken(tokens, buffer.toString().trim());
        }
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     * @throws BusinessException
     */
    public  List<String> tokenizeDocument(File file) throws IOException, BusinessException {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String ext = FilenameUtils.getExtension(file.getName());
        List<String> tokens;
        if("txt".equals(ext))
        {
            try (Scanner s = new Scanner(file)) {
                String str=new String();
                while(s.hasNext())
                {
                    str= str.concat(s.next()+" ");
                }
                
                tokens=process(str);
                for (int j = 0; j < tokens.size(); j++) {
                    System.out.println(tokens.get(j));
                }

                //System.out.printf("\nThe total number of tokens are :%d ", tokens.size());
                return tokens;
            }
        }
        else if("pdf".equals(ext))
        {
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper textstripper = new PDFTextStripper();
                String str = textstripper.getText(document);
                tokens=process(str);
                
                for (int j = 0; j < tokens.size(); j++) {
                    System.out.println(tokens.get(j));
                }

               // System.out.printf("\nThe total number of tokens are :%d ", tokens.size());
                document.close();
                return tokens;
            }
        
        }
        else
        {
                
        return null;
        }
    }

}
