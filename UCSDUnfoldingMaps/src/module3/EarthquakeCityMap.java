package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Siddharth Shukla
 * Date: December 14, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	int yellow = color(255, 255, 0);
    int red = color(255,0,0);
    int blue = color(0,0,255);

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	   
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	for(int i=0;i<earthquakes.size();i++){
	    	PointFeature f = earthquakes.get(i);
	    	// calling function to create SimplePointMarker
	    	markers.add(createMarker(f));
	    	//System.out.println(f.getProperties());
	    	
	    	// PointFeatures also have a getLocation method
	    	}
	    	//System.out.println(earthquakes.size()); 
	    	//Displays 279 data
	    }
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    
	    //TODO: Add code here as appropriate
	    map.addMarkers(markers);
	    
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		SimplePointMarker mark= new SimplePointMarker(feature.getLocation());
		Object magObj = feature.getProperty("magnitude");
    	float mag = Float.parseFloat(magObj.toString());
    	if(mag<4.0f){
    		mark.setRadius(5.0f);
    		mark.setColor(blue);
    		}
    	else if(mag<5.0f && mag>=4.0f)
    	{
    		mark.setRadius(8.0f);
    		mark.setColor(yellow);
    		}
    	else 
    	{
    		mark.setRadius(10.0f);
    		mark.setColor(red);
    		}
    	
    	return mark;
	}
	
	public void draw() {
	    background(100,200,50);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		String a,b,c,d;
		a="Earthquake key";
		b="5.0+ magnitude";
		c="4.0+ magnitude";
		d="below 4.0";
		textSize(16);
		fill(255,240,210);
		rect(25,50,150,300);
		
		fill(10,135,135);
		text(a,40,70);
		textSize(12);
		text(b,70,130);
		text(c,70,180);
		text(d,70,230);
		fill(255,0,0);
		ellipse(40,130,10,10);
		fill(255,255,0);
		ellipse(40,180,8,8);
		fill(0,0,255);
		ellipse(40,230,6,6);
	
	}
}
