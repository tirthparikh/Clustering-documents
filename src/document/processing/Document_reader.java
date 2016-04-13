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

public class Document_reader {
    
    //private File file;
    
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
    public void countCharacters(File file)
    {
        
    }
    
    
   
}
