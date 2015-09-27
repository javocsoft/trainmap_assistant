package es.javocsoft.exercise.trainmaphelper.beans;

import org.apache.commons.lang.StringUtils;

import es.javocsoft.exercise.trainmaphelper.exception.TrainMapException;

/**
 * A destination information.
 * 
 * @author Javier Gonzalez Serrano
 * @since Dec, 2014
 * @version 1.0.0
 *
 */
public class Destination implements Comparable<Object>{

	private String station;
	private int distance;

	/** Avoid an station instance without at least a name :) */
	@SuppressWarnings("unused")
	private Destination() {}
	
	/**
	 * Creates a station destination.
	 *  
	 * @param station	The station destination name
	 * @param distance	The distance to this destination
	 * @throws TrainMapException	If station or distance are not valid.
	 */
	public Destination(String station, String distance) throws TrainMapException{
		super();
		
		if(!Character.isLetter(station.charAt(0))){
			throw new TrainMapException("Not a valid destination station name. A letter is required!.");
		}
		if(!StringUtils.isNumeric(distance)){
			throw new TrainMapException("Not a valid distance!.");
		}
		
		this.station = station;
		this.distance = Integer.valueOf(distance);
	}

	
	// GETTERS & SETTERS

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	
	@Override
	public int compareTo(Object o) {
		return station.compareToIgnoreCase( ((Destination)o).getStation() );		
	}

}
