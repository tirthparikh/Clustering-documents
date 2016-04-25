/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.processing;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author tirth_parikh
 */
public class TF_IDF extends Kmeans{
    private ArrayList<String> total_words;
    
    public TF_IDF(ArrayList<Documents> f)
    {
        super(f);
    }
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
    
    
}
