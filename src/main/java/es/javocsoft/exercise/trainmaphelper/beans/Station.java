package es.javocsoft.exercise.trainmaphelper.beans;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A station.
 * 
 * @author Javier Gonzalez Serrano
 * @since Dec, 2014
 * @version 1.0.0
 *
 */
public class Station {

	private String name;
	/** All possible destinations where a station can carry you. */
	private SortedSet<Destination> destinations = null;

	
	
	/** Avoid an station instance without at least a name :) */
	protected Station() {
	}

	public Station(String name) {
		super();
		this.name = name;
	}

	public Station(String name, SortedSet<Destination> destinations) {
		super();
		this.name = name;
		this.destinations = destinations;
	}
	
	//PUBLIC
	
	public void addDestination(Destination destiny) {
		if(destinations==null) {
			//We use a TreeSet to avoid duplicates
			destinations = new TreeSet<Destination>();
		}
		
		destinations.add(destiny);
	}
	
	public Destination getDestination(String stationName) {
		Destination destination = null;
		if(destinations!=null){
			Iterator<Destination> it = ((Set<Destination>) destinations).iterator();
			while (it.hasNext()) {
				destination = (Destination)it.next();
		    	if(stationName.equalsIgnoreCase(destination.getStation())){
		    		break; 
		    	}else{
		    		destination = null;	
		    	}
		    }
		}	
		
		return destination;
	}
	
	
	//GETTERS & SETTERS

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortedSet<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(SortedSet<Destination> destinations) {
		this.destinations = destinations;
	}

}
