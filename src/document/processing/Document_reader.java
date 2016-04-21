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
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Document_reader {
    
   
    Vector token =new Vector();//could have used ArrayList as recommended by javadocs but I was used to this 
    ArrayList token1= new ArrayList();
    
    public File selectFile()
   {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    fileChooser.showOpenDialog(fileChooser);
    return new File(fileChooser.getSelectedFile().getAbsolutePath());
   }
    
    public void printpath(File file)
    {
        System.out.println("The path of the file you selected is "+file.getPath());
    }
   
    
    // This method reads a file, creates tokens (i.e single word) and print them
    // Currently it only excepts .txt file 
    
    public void tokenizeDocument(File file) throws FileNotFoundException, IOException
    {     
        //String to store the file extension
        String ext = FilenameUtils.getExtension(file.getName());
        
          
    if("txt".equals(ext))
    {
         Scanner s=new Scanner(file);
         s.useDelimiter(Pattern.compile("\\s|\\.|,"));
        
        int i=0;
        while(s.hasNext())
        {
            token.add(i, s.next());
            i++;
        }
        
        for(int j=0;j<i;j++)
            System.out.println(token.elementAt(j));
        
        System.out.printf("\nThe total number of tokens are :%d ",i);
    }
    
   
    else if("pdf".equals(ext))
    {       
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper textstripper= new PDFTextStripper();
                String str= textstripper.getText(document);
               // String[] ss = str.split("\\s\\ss\\ssss\\sss");
                int i=0;
               // for(i=0;i<ss.length;i++)
                 //   token.add(i,ss[i]);
                StringTokenizer st =new StringTokenizer(str);
                while(st.hasMoreTokens())
                    token.add(i++,st.nextToken());
                for(int j=0;j<i;j++)
                    System.out.println(token.elementAt(j));
                
                System.out.printf("\nThe total number of tokens are :%d ",i);
                document.close();
            }
    }
    else
            System.out.println("Please upload only .txt files for now.\n Sorry for the limitations to the file types, We are updating ourselves");
    }
}
