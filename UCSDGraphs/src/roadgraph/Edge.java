package roadgraph;

import geography.GeographicPoint;

public class Edge {
   private Node out; // To point to the "to" node 
   private String roadNameE;
   private String roadTypeE;
   private double lengthE;
   private Node in; // To point to the "from" node to track the bfs path
  
   Edge(Node out,String roadName,String roadType,double length){
	   this.out=out;
	   this.roadNameE=roadName;
	   this.roadTypeE=roadType;
	   this.lengthE=length;
       in=null;   
   }
  
  public Node getOut(){
	   return out;
   }
  public Node getIn(){
	   return in;
  }
  public void setIn(Node ne){
	   in=ne;
  }
   public String getRoadName(){
	   return roadNameE;
   }
   public String getRoadType(){
	   return roadTypeE;
   }
   public Double getLength(){
	   return lengthE;
   }
   public void setLength(double le){
	 lengthE=le;
   }
   public void setRoadName(String rn){
	   roadNameE=rn;
   }
   public void setRoadType(String rt){
	   roadTypeE=rt;
   }
}
