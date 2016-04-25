/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.processing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author tirth_parikh
 */
public class Documents {
    
    //instances
    
    private final File file;
    private final List<String> tokens;
    private ArrayList<Double> docVector;
    public Documents(File file1) throws IOException, BusinessException
    {   
        
        file=file1;
        tokens=Document_reader.tokenizeDocument(file);
    }
    public String getName()
    {
        return file.getName();
    }
    
    public List<String> get_tokens() throws IOException, BusinessException
    {
        return tokens;
    }
    
    public int number_of_tokens()
    {
        return tokens.size();
    }
    
    
    public File getFile()
    {
    return file;
    }

    double countOccurences(String get) {
       int count = 0;
        for (String token : tokens) {
            if (get == token) {
                count++;
            }
        }
       return count;
    }

    void add_component_to_vector(double TF_IDF) {
        docVector.add(TF_IDF);
    }
    
    ArrayList<Double> getVector()
    {
        return docVector;
    }
    
}
