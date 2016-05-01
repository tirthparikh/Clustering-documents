/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.processing;

import java.util.ArrayList;

/**
 *
 * @author tirth_parikh
 */
public class Centroid {
    private ArrayList<Double> points= new ArrayList<>();
    private ArrayList<Documents> docs= new ArrayList<>();
    private ArrayList<Double> newpoints= new ArrayList<>();
    public Centroid(ArrayList<Double> set_points)
    {
        points.addAll(set_points);
        newpoints=points;
    }

    ArrayList<Double> getpoints() {
        return points;
    }
    
    void AssignDocumentToCentroid(Documents d)
    {
        docs.add(d);
    }
    
    void clearlistOfDoc()
    {
        docs =new ArrayList<>();
    }
    
    void setPoints(ArrayList<Double> set_points)
    {
        points=new ArrayList<>();
        points.addAll(set_points);
    }
    
    ArrayList<Documents> getdocs()
    {
        return docs;
    }

    void setnewPoints(ArrayList<Double> temp) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        newpoints=new ArrayList<>();
        newpoints.addAll(temp);
    
    }
    
    boolean compareCentroidPoints()
    {
       return points.equals(newpoints);
    }
    
    void replaceOldPointsWithNew()
    {
        points=newpoints;
        newpoints=null;
    }
    
}
