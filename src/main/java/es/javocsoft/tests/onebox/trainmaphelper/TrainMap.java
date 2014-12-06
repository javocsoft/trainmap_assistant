package es.javocsoft.tests.onebox.trainmaphelper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import es.javocsoft.tests.onebox.trainmaphelper.beans.Destination;
import es.javocsoft.tests.onebox.trainmaphelper.beans.Station;
import es.javocsoft.tests.onebox.trainmaphelper.exception.TrainMapException;
import es.javocsoft.tests.onebox.trainmaphelper.utils.StringUtilities;


/**
 * Train Map service implementation. 
 * 
 * @author Javier Gonzalez Serrano
 * @since Dec, 2014
 * @version 1.0.0
 * 
 */
public class TrainMap {
	
	private String rawMap = null;
	
	/** Distance when a route does not exists */
	public static final int NO_SUCH_ROUTE = -1;
	
	/** Cache of all possible routes with distances */
	private Map<String, Integer> routesTableCache = new TreeMap<String, Integer>(new AlphabeticalComparator());
	
	/** Map routes data */
	private Map<String, Station> map = null;
	
	
	/**
	 * Initializes the map.
	 * 
	 * @param mapRaw	The raw station information data. A 
	 * 					comma separated list of values. Each value
	 * 					has <pre>[letter_station_from][letter_station_to][distance]</pre>
	 * 					<br>Example:<br><br>
	 * 					AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7
	 */
	public TrainMap(String mapRaw) {
		//Create map
		map = new TreeMap<String, Station>(new AlphabeticalComparator());
		
		//Clean all non desired characters
		rawMap = StringUtilities.normalize(mapRaw);
    	
    	//Create Map from string data
    	List<String> mapStationsRaw = Arrays.asList(rawMap.split(","));
    	for(String stationInfoRaw:mapStationsRaw){    		
    		try {
				putInMap(stationInfoRaw);
			} catch (TrainMapException e) {
				System.out.println("Invalid route specifications. Map route '" + stationInfoRaw + "' could not be added to the map: '" + e.getMessage() + "'.");
			} catch (Exception e) {
				System.out.println("Un-expected exception. Map route '" + stationInfoRaw + "'could not be added to the map: '" + e.getMessage() + "'.");
			}
    	}
    	
    	//Get all possible routes. I prefer to cache all routes to give
    	//an instant response than calculate each time the route. When
    	//someone needs a route, faster is better, people are no patient.
    	chachePossibleRoutes();
	}
	
	
	//PUBLIC
	
	/**
	 * Shows all map stations with data.
	 * 
	 */
	public void showStationsInformation() {
		Set<String> stationList = map.keySet();
    	Station stationInfo = null;
    	for(String s:stationList) {
    		stationInfo = map.get(s);
    		
    		System.out.println("Name: " + stationInfo.getName());
    		for(Destination dest:stationInfo.getDestinations()){
    			System.out.println("Destination: " + dest.getStation() + " Distance: " + dest.getDistance());
    		}
    	}
	}
	
	/**
	 * Prints the map with distances.
	 */
	public void showAllPossibleRoutes(){
		for(String s:routesTableCache.keySet()){
			System.out.println(s + " -> " + routesTableCache.get(s));
		}
	}
	
	/**
	 * Gets the route distance.
	 * 
	 * @param route	The desired route. Usage:
	 * 				<pre>[station_letter]-[station_letter]-[station_letter]....</pre>
	 * @return	The distance or -1 if such route does not exists.
	 */
	public int getRouteDistance(String route) {
		if(routesTableCache.containsKey(route)){
			return routesTableCache.get(route);
		}else{
			return NO_SUCH_ROUTE;
		}
	}
	
	/**
	 * Gives the shortest way to go from one statation to
	 * another.
	 * 
	 * @param from	The letter of the station
	 * @param to	The letter of the destination station
	 */
	public int getShortestDistanceBetweenTwoStations(String from, String to) {
		
		int shortestDistance = -1;
		int routeDistance = 0;
		for(String s:routesTableCache.keySet()){
			routeDistance = getRouteDistance(from + "-" + to);			
			if(routeDistance!=NO_SUCH_ROUTE && s.startsWith(from) && s.endsWith(to)){
				if(shortestDistance==-1 || routeDistance<shortestDistance){
					shortestDistance = routeDistance;
				}
			}
		}
		
		return shortestDistance;
	}
	
	/**
	 * Gets the raw data about train routes specified when 
	 * the train map was created.
	 * 
	 * @return
	 */
	public String getRawMap() {
		return rawMap;
	}
	
	
	//AUXILIAR
	
	/**
	 * Gets all map possible routes and store them.
	 */
	private void chachePossibleRoutes() {
		
		Station station = null;
		Set<String> stations = map.keySet();
		for(String s:stations){
			station = map.get(s);
			searchForStation(null,0, station);			
		}
	}
	
	/**
	 * Iterates through the possible destinations of an station
	 * recursively for each destination station.
	 * 
	 * @param referralName	If the stations was reached from another
	 * 						station the path is set here.
	 * @param referralDistance	The distance until this station.
	 * @param station	The station to process
	 */
	private void searchForStation(String referralName, int referralDistance, Station station) {
		Destination destination = null;
		if(station!=null && station.getDestinations()!=null){
			Iterator<Destination> it = ((Set<Destination>) station.getDestinations()).iterator();
			String routePath = null;
			while (it.hasNext()) {
				destination = (Destination)it.next();
				//We avoid being stuck when two stations communicates each other
				if(referralName!=null && referralName.contains(station.getName() + "-" + destination.getStation())){
					continue;
				}else{
					if(!StringUtils.isEmpty(referralName)){
						routePath = referralName + "-" + destination.getStation();
					}else{
						routePath = station.getName() + "-" + destination.getStation();
					}
					
					if(!routesTableCache.containsKey(routePath)){				
						routesTableCache.put(routePath , referralDistance + destination.getDistance());									
						searchForStation(routePath, referralDistance + destination.getDistance(), map.get(destination.getStation()));
					}
				}
		    }
		}
	}	
	
	/**
	 * Put in the map the route specification.
	 * 
	 * @param stationInfoRaw
	 * @throws Exception
	 */
	private void putInMap(String routeSpecification) throws TrainMapException, Exception {
		
		//Split map point parts.
		String[] stationRawParts = routeSpecification.split("(?<=\\G.{1})");
		if(stationRawParts.length!=3){
			throw new TrainMapException("Not a valid map route. use <origin[letter(A-D)]><destiny[letter(A-D)]><distance>.");
		}else{
			Station station = null;
			if((station = map.get(stationRawParts[0]))==null){
				//Create station and add to the map
				if(!Character.isLetter(stationRawParts[0].charAt(0))){
					throw new TrainMapException("Not a valid station name. A letter is required!.");
				}
				station = new Station(stationRawParts[0]);
				map.put(station.getName(), station);
			}
			
			//Add destination to the station
			Destination destiny = new Destination(stationRawParts[1], stationRawParts[2]);
			station.addDestination(destiny);
		}
	}	
	
	
	/**
	 * Station comparator for alphabetical ordering.
	 */
	class AlphabeticalComparator implements Comparator<String> {  
	    @Override  
	    public int compare(String a, String b) {  
	        return a.compareToIgnoreCase( b );  
	    }  
	}
}
