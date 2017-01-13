package com.project.event;

/**
 * Simple class Event that has a constructor, getters and setters, as well as modified toString() method.
 * 
 * @author kate
 *
 */

public class Event {

	private String titleOfEvent;
	private Type typeOfEvent;
	private Marker markerOfEvent;
	private String dateOfEvent;
	private String timeOfEvent;
	private String descriptionOfEvent;

	public Event() {};

	public Event(String titleOfEvent, Type typeOfEvent, Marker markerOfEvent, String dateOfEvent, String timeOfEvent,
			String descriptionOfEvent) {
		this.titleOfEvent = titleOfEvent;
		this.typeOfEvent = typeOfEvent;
		this.markerOfEvent = markerOfEvent;
		this.dateOfEvent = dateOfEvent;
		this.timeOfEvent = timeOfEvent;
		this.descriptionOfEvent = descriptionOfEvent;
	}

	public String getTitleOfEvent() {
		return titleOfEvent;
	}

	public void setTitleOfEvent(String nameOfEvent) {
		this.titleOfEvent = nameOfEvent;
	}

	public Type getTypeOfEvent() {
		return typeOfEvent;
	}

	public void setTypeOfEvent(Type typeOfEvent) {
		this.typeOfEvent = typeOfEvent;
	}

	public Marker getMarkerOfEvent() {
		return markerOfEvent;
	}

	public void setMarkerOfEvent(Marker markerOfEvent) {
		this.markerOfEvent = markerOfEvent;
	}

	public String getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(String dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	public String getTimeOfEvent() {
		return timeOfEvent;
	}

	public void setTimeOfEvent(String timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}

	public String getDescriptionOfEvent() {
		return descriptionOfEvent;
	}

	public void setDescriptionOfEvent(String descriptionOfEvent) {
		this.descriptionOfEvent = descriptionOfEvent;
	}

	public String toString() {
		return "'" + this.titleOfEvent +  "' on "
		+ this.getDateOfEvent() + " at " + this.getTimeOfEvent() +" (" + this.getTypeOfEvent() + ", " + this.getMarkerOfEvent() + "). Description: '"
		+ this.getDescriptionOfEvent() + "'";

	}
}
