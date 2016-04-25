package document.processing;

import java.io.IOException;
import java.util.ArrayList;



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
    

public ArrayList<Documents> pool_of_Documents= new ArrayList<>();
private ArrayList<String> total_words;

    public Kmeans(ArrayList<Documents> f )
        {
            pool_of_Documents=f;
        }
    public  void createCluster() throws IOException, BusinessException{
    
    calculate_total_words();
    doc_to_vec();
    
    
    
    }
//{       List<String>[] tokens=new List[pool_of_Documents.size()];
//        
//        Kmeans k=new Kmeans();
//            
//            
//            //Create tokens from every document
//            for (int i=0;i<pool_of_Documents.size();i++)
//                {
//                    try 
//                        {
//                        
//                            tokens[i]=Document_reader.tokenizeDocument(pool_of_Documents.get(i).getFile());
//                            //System.out.println(pool_of_Documents.get(i).getName());
//                        }
//                    catch (BusinessException ex)
//                        {
//                            Logger.getLogger(Kmeans.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    //Create a list of total individual terms
//                    for(int j=0;j<tokens[i].size();j++)
//                    {
//                        if(!k.total_words.contains(tokens[i].get(j)))
//                        {
//                            k.total_words.add(tokens[i].get(j));
//                        }
//                    
//                    }
//                   
//                    for (String total_word : k.total_words) 
//                    {
//                        System.out.println(total_word);
//                    }
//                    
//                    System.out.println("Length of total words = "+k.total_words.size());
//                }
//            
//}
//
//    public Kmeans() {
//        this.total_words = new ArrayList<>();
//    }
    
    void calculate_total_words() throws IOException, BusinessException
    {
        for(int i=0;i<pool_of_Documents.size();i++)
        {
           for(int j=0;j<pool_of_Documents.get(i).number_of_tokens();j++)
                    {
                        if(!total_words.contains(pool_of_Documents.get(i).get_tokens().get(j)))
                        {
                            total_words.add(pool_of_Documents.get(i).get_tokens().get(j));
                        }
                    
                    }
        }
    
    }

 
 public void start_k_means(int Documents[],int num_of_cluster)
 {
     
 
 }

    private void doc_to_vec() throws IOException, BusinessException {
        for(int i=0;i<pool_of_Documents.size();i++)
        {
            for(int j=0;j<total_words.size();j++)
            {
               double TF=pool_of_Documents.get(i).countOccurences(total_words.get(j))/pool_of_Documents.get(i).number_of_tokens();
               double IDF=pool_of_Documents.size()/No_doc_with_terms(total_words.get(j));
               double TF_IDF=TF*IDF;
               pool_of_Documents.get(i).add_component_to_vector(TF_IDF);
            }
        }
    }

    private int No_doc_with_terms(String get) throws IOException, BusinessException {
    int count = 0;
       
        for(int a=0;a<pool_of_Documents.size();a++)
           if(pool_of_Documents.get(a).get_tokens().contains(get))
               count++;
        return count;
    }
 

 }
