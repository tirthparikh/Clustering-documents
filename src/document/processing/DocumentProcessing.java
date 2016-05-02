
package document.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 *
 * @author tirth_parikh
 */
public class DocumentProcessing {

    public static void main(String[] args) throws FileNotFoundException, IOException, BusinessException, InterruptedException, Exception {
        
        ArrayList<Documents> listOfDocuments=new ArrayList<>();
        File dir = new File("C:\\Users\\tirth_parikh\\Desktop\\Test");
        
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : files) {
	
                    listOfDocuments.add(new Documents(file));
		}
                
//        for(Documents doc: listOfDocuments)
//            System.out.println(doc.getName());
        Kmeans k=new Kmeans(listOfDocuments);
                            k.createCluster();
//        ArrayList<Documents> listOfDocuments=new ArrayList<>();
//        //Document_reader documents=new Document_reader();
//        
//        
//        System.out.println("Hello, enter to Y start clustering your documents or N to exit :");
//        Scanner s = new Scanner(System.in);
//        String s1=s.nextLine();
//        
//        //Case 1: The user entered Y or y
//        if ("y".equals(s1) || "Y".equals(s1)) {
//            
//            //Warning Message for the limitations of the document
//            System.out.println("\n\nCurrently you can only upload at most 25 documents."
//                                +"\nAlso, please upload only pdf or txt files."
//                                +"\nThank you. ");
//            
//            //Just a 2 second pause to let user read warning
//            try {
//                Thread.sleep(2000);                 //1000 milliseconds is one second.
//            } catch (InterruptedException ex) {
//                Thread.currentThread().interrupt();
//            }
//            
//            
//            //for loop to let user entered upto 25 documents
//            for (int i = 0; i < 25; i++) {
//
//                System.out.println("\n\nEnter y to upload a document and n to stop : ");
//                s1 = s.nextLine();
//
//                
//                if ("y".equals(s1) || "Y".equals(s1)) {
//
//                    //add the document to the pool of documents
//                    listOfDocuments.add(i, new Documents(Document_reader.selectFile()));//new changes 
//                    System.out.println("Document added");
//                    if (i == 24) {
//                        System.out.println("You have uploaded 25 documents,you cannot enter more for the time being. "
//                                       + "\nEnter s to start k-means clustering:  ");
//
//                        s1 = s.nextLine();
//
//                        if ("s".equals(s1) || "S".equals(s1)) {
//                            //start k-means clustering
//                            System.out.println("Clustering starts");
//                            Kmeans k=new Kmeans(listOfDocuments);
//                            k.createCluster();
//                        }
//                    }
//                } else if ("N".equals(s1) || "n".equals(s1)) {
//                    System.out.printf("\nYou have uploaded %d documents", i);
//
//                    if (i == 0) {
//                        System.out.println("\nPlease upload atleast one or atmost 10 documents to do the clustering");
//                    } else {
//                        System.out.println("Press s to start k-means clustering:  ");
//
//                        s1 = s.nextLine();
//
//                        if ("s".equals(s1) || "S".equals(s1)) {
//                            //start k-means clustering
//                            System.out.println("Clustering starts");
//                           Kmeans k=new Kmeans(listOfDocuments);
//                            k.createCluster();
//
//                        }
//
//                    }
//
//                    break;
//                } else {
//                    System.out.println("You have entered " + s1 + ".Please enter y or n for uploading document.\n Thank you!");
//                }
//            }
//        }
//        
//        //Case 2: The user entered n or N
//        else if ("n".equals(s1)||"N".equals(s1))
//        {  
//            System.exit(0);
//        }
//        
//        //Case 3: The user Entered something other than Y or N
//        else
//        {
//           
//            for(int k=0;k<100;k++)
//                System.out.println("\n");
//            System.out.println("You have entered "+s1+"");
//            DocumentProcessing.main(args);
//            System.exit(0);
//        }

    }

}
