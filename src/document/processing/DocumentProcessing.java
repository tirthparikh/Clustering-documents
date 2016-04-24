/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author tirth_parikh
 */
public class DocumentProcessing {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws document.processing.BusinessException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, BusinessException {
        
        ArrayList<File> listOfDocuments=new ArrayList<>();
        Document_reader documents=new Document_reader();
        System.out.println("Hello, enter to Y start clustering your documents or N to exit :");
        Scanner s = new Scanner(System.in);
        String s1=s.nextLine();
        
        if ("y".equals(s1) || "Y".equals(s1)) 
        {   
            
            System.out.println("\n\nCurrently you can only upload at most 10 documents.\nAlso, please upload only pdf or txt files.\nThank you. ");
            
            try {
                    Thread.sleep(2000);                 //1000 milliseconds is one second.
                }
            catch(InterruptedException ex) 
            {
                    Thread.currentThread().interrupt();
            }
            
            for (int i = 0; i < 10; i++) 
            {
                
               
                
                System.out.println("\n\nEnter y to upload a document and n to stop : ");
                   
                    s1=s.nextLine();
                    
                
                if ("y".equals(s1) ||"Y".equals(s1)) 
                {   
                     
                    //add the document to the pool of documents
                    listOfDocuments.add(i,documents.selectFile());//new changes 
                    System.out.println("Document added");
                    if (i == 9) 
                {
                    System.out.println("You have uploaded 10 documents \nEnter s to start k-means clustering:  ");
                   
                    s1=s.nextLine();
                    
                    if ("s".equals(s1)||"S".equals(s1)) 
                    {   
                        //start k-means clustering
                        System.out.println("Clustering starts");
                        Kmeans.createCluster(listOfDocuments);
                    }
                }
                }
                
                else if ("N".equals(s1) ||"n".equals(s1))
                {
                    System.out.printf("\nYou have uploaded %d documents",i);
                
                    if (i == 0) 
                    {
                        System.out.println("\nPlease upload atleast one or atmost 10 documents to do the clustering");
                    }
                    else
                    {
                        System.out.println("Press s to start k-means clustering:  ");
                        
                       
                        s1=s.nextLine();
                       
                        if ("s".equals(s1)||"S".equals(s1)) 
                        {   
                        //start k-means clustering
                        System.out.println("Clustering starts");
                        Kmeans.createCluster(listOfDocuments);
                        
                        }

                    }
                    
                    break;
                }
                else 
                {   
                    System.out.println("You have entered " + s1 + ".Please enter y or n for uploading document.\n Thank you!");
                }
            }
        }
        else if (s1=="n"||s1=="N")
        {  
            System.exit(0);
        }
        else
        {
            
            System.out.println("You have entered "+s1+".\nEnter Y to start clustering your documents or N to exit:");
            DocumentProcessing.main(args);
        }

    }

}
