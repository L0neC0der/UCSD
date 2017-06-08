package roadgraph;

import java.util.ArrayList;

import geography.GeographicPoint;

public class Node implements Comparable {
	private GeographicPoint g; // Node stores the geographic point
	private ArrayList<Edge> ed; // Node stores the arraylist of edges the node points to
	private Edge rev; // Node points to edge from where it was reached to track the path of bfs
	private double distance;// Distance of the node from the source node 
	private double distanceDij;// used in calculating A* 
    Node(GeographicPoint g){
    	this.g=g;
    	ed=new ArrayList<Edge>();
    	rev=null;
    	distance=0.0;
    }
    public GeographicPoint getGeoPoint(){
    	return g;
    }
    public void addEdge(Edge e){
    	ed.add(e);
    }
    public ArrayList<Edge> getEdgeList(){ // To get the list of edges the node points to
    	return ed;
    }
    public void setRev(Edge re){ // To set the link to the edge from where it was reached
    	rev=re;
    }
    public Edge getRev(){
    	return rev;
    }
    public void setDistance(double d){
   
    	this.distance=d;
    }
    public double getDistance(){
    	return distance;
    }
    public void setDistanceDij(double d){
    	this.distanceDij=d;
    }
    public double getDistanceDij(){
    	return distanceDij;
    }
    public int compareTo(Object n){
    	Node no=(Node) n;
    	return ((Double)this.getDistance()).compareTo((Double)no.getDistance());
    }
    
    
}
