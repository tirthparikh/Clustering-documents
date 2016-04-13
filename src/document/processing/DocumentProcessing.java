/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.processing;

import java.io.File;

/**
 *
 * @author tirth_parikh
 */
public class DocumentProcessing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       Document_reader test1= new Document_reader();
       File file= test1.selectFile();
       test1.printpath(file);
       
    }
    
}
