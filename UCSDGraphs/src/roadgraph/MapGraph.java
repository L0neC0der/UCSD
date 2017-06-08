/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	private int numVertices;
	private int numEdges;
	private double totalLength;
	private static int countDij=0;
	private static int countA=0;
	private HashMap<GeographicPoint,Node> nodePair;// For maintaining the node(vertex) pointer for every Geographic Point 
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2 --------------------------------
		numVertices=0;
		numEdges=0;
		nodePair=new HashMap<GeographicPoint,Node>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2 ----------------------------------------------
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2 ------------------------------------
		Set<GeographicPoint> ret=new HashSet<GeographicPoint>();
		ret=nodePair.keySet();
		return ret;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2 ---------------------------------------------
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2 ------------------------------------------
		boolean check=false;
		if (!location.equals(null)){
			if(!nodePair.containsKey(location)){
				Node temp=new Node(location);
				nodePair.put(location,temp);
				numVertices++;
				check=true;
				}
		}
		
		return check;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2 ---------------------------------------
		if(length>=0&&nodePair.containsKey(from)&&nodePair.containsKey(to)
				&&!from.equals(null)&&!to.equals(null)){
		// Creating an Edge object with all the data and pointer to the "to" node 	
			Edge tempE=new Edge(nodePair.get(to),roadName,roadType,length);
		// Linking the "from" node to the Edge	
			nodePair.get(from).addEdge(tempE);
		    numEdges++;
		    totalLength+=length;
		}
		else{
			throw new IllegalArgumentException();
		}
	
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public  List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public  List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		List<GeographicPoint> wap=new ArrayList<GeographicPoint>();// For returning the bfs path
		HashSet<Node> visited=new HashSet<Node>();// For checking the visited nodes
		Queue<Node> q=new LinkedList<Node>();
	
		
		
		Node curr=nodePair.get(start);
		
		if(curr.getEdgeList().isEmpty()){
			return null;
		}
		q.add(curr);
		visited.add(curr);
		
		nodeSearched.accept(start);
		while(!q.isEmpty() ){
			
			curr=q.remove();
			nodeSearched.accept(curr.getGeoPoint());
			if(curr.equals(nodePair.get(goal))){
				// After reaching the goal, tracing the bfs path from the "goal" to "start" node
				while(!curr.equals(nodePair.get(start))){
					wap.add(curr.getGeoPoint());
					curr=curr.getRev().getIn();
					
				}
				wap.add(curr.getGeoPoint());
				// The traced path is in reverse order so to make it correct
				Collections.reverse(wap);
			    return wap;
			  }
				
				
				
				for(Edge te:curr.getEdgeList()){
					if(!visited.contains(te.getOut())){
						visited.add(te.getOut()); // To mark the nodes as visited
						q.add(te.getOut()); // Adding the node to queue
						te.setIn(curr);  // Setting(linking) the reverse path from the edge to the curr node to trace the bfs path 
						te.getOut().setRev(te); // Setting(linking) the reverse path from the "to" node to the edge to trace the bfs path
						
					}
				  }
				
		}
		

		return wap;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3 ++++++++++++++++++++++++++++++++++++

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		for(GeographicPoint tra:this.getVertices()){
			nodePair.get(tra).setDistance(totalLength);
		}
		HashSet<Node> visited=new HashSet<Node>();
		PriorityQueue<Node> q=new PriorityQueue<Node>();
		List<GeographicPoint> wap=new ArrayList<GeographicPoint>();
		Node curr=nodePair.get(start);
		curr.setDistance(0.0);
		q.add(curr);
		
		while(!q.isEmpty()){
			curr=q.poll();
			
			
			countDij++;
			if(!visited.contains(curr)){
				visited.add(curr);
				nodeSearched.accept(curr.getGeoPoint());
				if(curr.equals(nodePair.get(goal))){
					while(!curr.equals(nodePair.get(start))){
						wap.add(curr.getGeoPoint());
						curr=curr.getRev().getIn();
						
					}
					wap.add(curr.getGeoPoint());
					// The traced path is in reverse order so to make it correct
					Collections.reverse(wap);
				    return wap;
				}
				for(Edge te:curr.getEdgeList()){
					if(!visited.contains(te.getOut())){
						if(te.getOut().getDistance()>curr.getDistance()+te.getLength())//updating distance
						{
							te.getOut().setDistance(curr.getDistance()+te.getLength());
							te.setIn(curr);  // Setting(linking) the reverse path from the edge to the curr node to trace the bfs path 
							te.getOut().setRev(te); // Setting(linking) the reverse path from the "to" node to the edge to trace the bfs path
						}
						
						q.add(te.getOut()); // Adding the node to queue
						
						
					}
				  }
				
			}
			
		}
		
		return wap;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3 +++++++++++++++++++++++++++++++++++++++++++
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		for(GeographicPoint tra:this.getVertices()){
			nodePair.get(tra).setDistance(totalLength*totalLength+start.distance(goal));
		}
		for(GeographicPoint tra:this.getVertices()){
			nodePair.get(tra).setDistanceDij(totalLength);
		}
		HashSet<Node> visited=new HashSet<Node>();
		PriorityQueue<Node> q=new PriorityQueue<Node>();
		List<GeographicPoint> wap=new ArrayList<GeographicPoint>();
		Node curr=nodePair.get(start);
		curr.setDistance(start.distance(goal));
		curr.setDistanceDij(0.0);
		q.add(curr);
		
		while(!q.isEmpty()){
			curr=q.poll();
			countA++;
			if(!visited.contains(curr)){
				visited.add(curr);
				nodeSearched.accept(curr.getGeoPoint());
				if(curr.equals(nodePair.get(goal))){
					while(!curr.equals(nodePair.get(start))){
						wap.add(curr.getGeoPoint());
						curr=curr.getRev().getIn();
						
					}
					wap.add(curr.getGeoPoint());
					// The traced path is in reverse order so to make it correct
					Collections.reverse(wap);
				    return wap;
				}
				for(Edge te:curr.getEdgeList()){
					if(!visited.contains(te.getOut())){
						if(te.getOut().getDistanceDij()>curr.getDistanceDij()+te.getLength())//updating distance
						{
							te.getOut().setDistanceDij(curr.getDistanceDij()+te.getLength());
							te.getOut().setDistance(curr.getDistanceDij ()+te.getLength()+te.getOut().getGeoPoint().distance(goal));
							te.setIn(curr);  // Setting(linking) the reverse path from the edge to the curr node to trace the bfs path 
							te.getOut().setRev(te); // Setting(linking) the reverse path from the "to" node to the edge to trace the bfs path
						}
						
						q.add(te.getOut()); // Adding the node to queue
						
						
					}
				  }
				
			}
			
		}
		
		return wap;
		
	
	}

	
	
	public static void main(String[] args)
	{
		/*System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		*/
		//GeographicPoint start = new GeographicPoint(7.0,3.0);
		//GeographicPoint end = new GeographicPoint(5.0, 1.0);
		//System.out.println(bfs(start,end));
		//System.out.println("DONE.");
		// You can use this method for testing.  
		
		// Use this code in Week 3 End of Week Quiz ++++++++++++++++++++++++++++++++++
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		System.out.println("dij val: "+countDij+" A* val:"+countA);
		
	}
	
}
