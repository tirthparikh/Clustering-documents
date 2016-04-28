package document.processing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

    public ArrayList<Documents> pool_of_Documents = new ArrayList<>();
    private final ArrayList<String> total_words = new ArrayList<>();

    public Kmeans(ArrayList<Documents> f) {
        pool_of_Documents = f;
    }

    public void createCluster() throws IOException, BusinessException {

        calculate_total_words();
        doc_to_vec();
        print_TFIDF();
//        for(int cardinality=3;cardinality<=10;cardinality++)
//        {
//            start_K_means(pool_of_Documents,cardinality);
//            
//        }

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

    void calculate_total_words() throws IOException, BusinessException {
        for (int i = 0; i < pool_of_Documents.size(); i++) {
            for (int j = 0; j < pool_of_Documents.get(i).number_of_tokens(); j++) {

                if (!total_words.contains(pool_of_Documents.get(i).get_tokens().get(j))) {
                    total_words.add(pool_of_Documents.get(i).get_tokens().get(j));
                }

            }

        }

    }

    

    private void doc_to_vec() throws IOException, BusinessException {
        for (int i = 0; i < pool_of_Documents.size(); i++) {
            for (int j = 0; j < total_words.size(); j++) {
                double TF = pool_of_Documents.get(i).countOccurences(total_words.get(j)) / pool_of_Documents.get(i).number_of_tokens();
                double IDF = (double)Math.log(pool_of_Documents.size() / No_doc_with_terms(total_words.get(j)));
                double TF_IDF = TF * IDF;
                pool_of_Documents.get(i).add_component_to_vector(TF_IDF);
            }
            pool_of_Documents.get(i).normalized_Vector();
            
        }
    }

    private int No_doc_with_terms(String get) throws IOException, BusinessException {
        int count = 0;

        for (int a = 0; a < pool_of_Documents.size(); a++) {
            if (pool_of_Documents.get(a).get_tokens().contains(get)) {
                count++;
            }
        }
        return count;
    }

    private void print_TFIDF() {
        String p = "{";
        for (int i = 0; i < pool_of_Documents.size(); i++) {
            //p=p+pool_of_Documents.get(i).getName()+" ,";
            if (i == pool_of_Documents.size() - 1) {
                p = p + pool_of_Documents.get(i).getName() + " }";
            } else {
                p = p + pool_of_Documents.get(i).getName() + " ,";
            }

        }
        System.out.println("The list of Documents you uploaded is: ");
        System.out.println(p);
        System.out.println();
        System.out.println("The TF_IDF table for the documents");
        System.out.println("===========================================================================================================================");
        String s = "                         "; //6-tabs
        for (int i = 0; i < pool_of_Documents.size(); i++) {
            s = s + "    " + "Document" + i;
        }//4 spaces
        System.out.println(s);
        for (int i = 0; i < total_words.size(); i++) {
            String termsScore;
            
            if(total_words.get(i).length()>=25)
            termsScore=total_words.get(i).substring(0, 24);
            else
                termsScore=total_words.get(i);
            for(int w=total_words.get(i).length();w<=25;w++)
                termsScore=termsScore.concat(" ");
//            for(int c=0;c<pool_of_Documents.size();c++)
//                termsScore=termsScore.concat("    "+pool_of_Documents.get(c).countOccurences(total_words.get(i))+"  ");
            for(int c=0;c<pool_of_Documents.size();c++)
                termsScore=termsScore.concat("    "+pool_of_Documents.get(c).getVector().get(i).toString()+"  ");
            System.out.println(termsScore);
        }

    }

    private void start_K_means(ArrayList<Documents> pool_of_Documents, int cardinality2) {
       ArrayList<Centroid> centroids;
       //Place random cluster centroids
       centroids=calculate_random_initial_centroids(pool_of_Documents,cardinality2);
       //Assign each object to the closet centroid
       for(int pod=0;pod<pool_of_Documents.size();pod++)
       {   
           double distance[]=new double[cardinality2];
           for(int cluster=0;cluster<cardinality2;cluster++)
           {
               distance[cluster]=Edistance(centroids.get(cluster),pool_of_Documents.get(pod).getUnitVector());
           }
           int index= closest_centroid(distance);
           centroids.get(index).AssignDocumentToCentroid(pool_of_Documents.get(pod));
           
       }
       
       //recompute the centroids
       
       
    }
    
    
    
    //Kmeans++
    private ArrayList<Centroid> calculate_random_initial_centroids(ArrayList<Documents> pool_of_Documents,int cardinality1) {
        //Implementing Kmeans++ for initial seeding
        
        ArrayList<Centroid> initial_centroids= new ArrayList<>();
        double[] distance = new double[pool_of_Documents.size()];
        //step1:Choose one center uniformly at random from among the data points
        initial_centroids.add(0, random_data_point());
        //step4:Repeat Steps 2 and 3 until k centers have been chosen
        for(int k=1;k<cardinality1;k++){
        //step2:For each data point x, compute D(x), the distance between x and the nearest center that has already been chosen
        for(int datapoints=0;datapoints<pool_of_Documents.size();datapoints++)
        {
            distance[datapoints]=Edistance(initial_centroids.get(k-1),pool_of_Documents.get(datapoints).getUnitVector());
        }
        //step3:Choose one new data point at random as a new center, using a weighted probability distribution where a point x is chosen with probability proportional to D(x)^2.
        //storing probabilities in place of distance
        double sum=0;
                for(int datapoints=0;datapoints<distance.length;datapoints++)
                    sum+=(distance[datapoints]*distance[datapoints]);
                for(int datapoints=0;datapoints<distance.length;datapoints++)
                    distance[datapoints]=(distance[datapoints]*distance[datapoints])/sum;
        //choosing new datapoint
              Centroid cq= probable_datapoints(distance);
            int count = 0;
              for(int s=0;s<initial_centroids.size();s++)
                  if(initial_centroids.get(s).equals(cq))
                      count++;
              if(count==0)
                  initial_centroids.add(k, cq);
              else 
                  k--;
              
        }
   return initial_centroids;
    }

    private Centroid random_data_point() {
        Random randomGenerator= new Random();
        int index=randomGenerator.nextInt(pool_of_Documents.size());
        return new Centroid(pool_of_Documents.get(index).getUnitVector());
    }

    private double Edistance(Centroid get, ArrayList<Double> unitVector) {
        double d;
        double distance=0;
        if(get.getpoints().size()==unitVector.size())
        {
            
            for(int component=0;component<get.getpoints().size();component++)
            {
                d=get.getpoints().get(component)-unitVector.get(component);
                distance+= d*d;
            }
        
        }
        else 
        {
            throw new IllegalArgumentException("Number of Components do not match");
        }
       return Math.sqrt(distance); 
    }

    private Centroid probable_datapoints(double[] distance) {
        int prob;
        for(prob=0;prob<distance.length;prob++)
            if(Math.random()<distance[prob])
                break;
        return new Centroid(pool_of_Documents.get(prob).getUnitVector());
    }

    private int closest_centroid(double[] distance) {
       int index=0;
       
        for (int i = 1; i < distance.length; i++) 
        {
            if(distance[i-1]>distance[i])
                index=i;
        }
        
        return index;
    }


}
