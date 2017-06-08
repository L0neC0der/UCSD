package module2_Self;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	// Decleration of the field variables
UnfoldingMap map;
HashMap<String,Float> lifeExpCountries;
List<Feature > countries;
List<Marker > countryMarkers;


public void setup(){
	size(800,800,OPENGL);
	map= new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
	MapUtils.createDefaultEventDispatcher(this, map);
	
	//loading life expectancy data from file
	lifeExpCountries=loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
	println("Loaded " + lifeExpCountries.size() + " data entries");
	
	//loading feature i.e. the polygon of the countries
	countries=GeoJSONReader.loadData(this,"countries.geo.json");
	
	//creating and adding the markers on the countries
	countryMarkers = MapUtils.createSimpleMarkers(countries);
	map.addMarkers(countryMarkers);
	
	/*for(Marker marker: countryMarkers){
	    String countryId= marker.getId();
	
	System.out.println(countryId);
	}*/
	//helper method to colour the countries according to the data given
	shadeCountries();
	
}
public void draw(){
	map.draw();
}

// All helper methods are here

//Helper method1 to load data from the CSV file
private HashMap<String,Float> loadLifeExpectancyFromCSV(String filename){
	HashMap<String, Float> lifeExpMap = new HashMap<String,Float>();
	String[] rows=loadStrings(filename);
	for (String row:rows){
		String[] columns=row.split(",");
		if(columns.length==6 && !columns[5].equals("..")){
		lifeExpMap.put(columns[4],Float.parseFloat(columns[5]));
		//System.out.println(columns[0]+"||"+columns[1]+"||"+columns[2]+"||"+columns[3]+"||"+columns[4]+"||"+columns[5]+"||");
		//boolean a=lifeExpMap.containsKey(columns[3]);
		//System.out.println(a);
		}
		}
	return lifeExpMap;
}

//Helper method2 to color countries accordingly
private void shadeCountries(){
	for(Marker marker: countryMarkers){
	    String countryId= marker.getId();
	    
	    									//boolean a=lifeExpMap.containsKey(countryId);
	    									//System.out.println(a);
	    if(lifeExpCountries.containsKey(countryId)){
	    	float lifeExp= lifeExpCountries.get(countryId);
	    	int colorLevel=(int) map (lifeExp,40,90,10,255);
	   
	    	marker.setColor(color(255-colorLevel,100,colorLevel));
	    }
	    else marker.setColor(color(150,150,150));
	}
}
}
