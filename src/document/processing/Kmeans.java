package document.processing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tirth_parikh
 */
public class Kmeans {
    

public static void createCluster(ArrayList<File> pool_of_Documents)
{       List<String>[] tokens=new List[pool_of_Documents.size()];
        Document_reader dr=new Document_reader();
        ArrayList<String> total_words=new ArrayList<>();    
            
            
            //Create tokens from every document
            for (int i=0;i<pool_of_Documents.size();i++)
                {
                    try 
                        {
                            tokens[i]=dr.tokenizeDocument(pool_of_Documents.get(i));
                            //System.out.println(pool_of_Documents.get(i).getName());
                        }
                    catch (IOException | BusinessException ex)
                        {
                            Logger.getLogger(Kmeans.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    //Create a list of total individual terms
                    for(int j=0;j<tokens[i].size();j++)
                    {
                        if(!total_words.contains(tokens[i].get(j)))
                        {
                            total_words.add(tokens[i].get(j));
                        }
                    
                    }
                   
                    for (String total_word : total_words) 
                    {
                        System.out.println(total_word);
                    }
                    
    System.out.println("Length of total words = "+total_words.size());
                }
            
            
            
            
}
    
    
private void calculate_TF()
{



}
    
private void calculate_IDF()
{



}
    
    
 private void calculate_TF_IDF()
 {
     
     
     
 }
 
 public void start_k_means(int Documents[],int num_of_cluster)
 {
     
 
 }
 

    
    
    
    
}
