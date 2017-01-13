package com.project.event;

import java.util.Scanner;

import com.project.database.Validator;

/**
 * This class takes care of the creation and validation of new events
 * 
 * @author kate
 *
 */

public class Creator implements Validator {

	/**
	 * Creates event by asking user for input and validating it on the spot
	 * 
	 * @return Event 	this is the newly created event that will be imported into the DB
	 */
	
	public static Event createEvent() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		scanner.reset();
		Event newEvent = new Event();

		// title
		System.out.println("Enter the title of your event (up to 30 characters): ");
		String title = scanner.nextLine();
		while (!validate("title", title)) {
			System.out.println("That's not a valid title of event!");
			title = scanner.nextLine();
		}
		newEvent.setTitleOfEvent(title);

		// type
		System.out.println("Enter M for Meeting or T for task: ");
		while (!scanner.hasNext("[mtMT]{1}")) {
			System.out.println("That's not a valid type of event!");
			scanner.nextLine();
		}
		String type = scanner.nextLine().toUpperCase();
		switch (type) {
		case "M":
			newEvent.setTypeOfEvent(Type.MEETING);
			break;
		default:
			newEvent.setTypeOfEvent(Type.TASK);
		}

		// marker
		System.out.println("Enter 1 for Public, 2 for Confidential or 3 for Private: ");
		while (!scanner.hasNext("[123]{1}")) {
			System.out.println("That's not a valid marker of event!");
			scanner.nextLine();
		}
		String marker = scanner.nextLine().toUpperCase();
		switch (marker) {
		case "1":
			newEvent.setMarkerOfEvent(Marker.PUBLIC);
			break;
		case "2":
			newEvent.setMarkerOfEvent(Marker.CONFIDENTIAL);
			break;
		default:
			newEvent.setMarkerOfEvent(Marker.PRIVATE);
		}

		// date
		System.out.println("Enter date of event in format DD.MM.YYYY: ");
		while (!scanner.hasNext(
				"(((0[1-9]|1[0-9]|2[0-8])[.](0[1-9]|1[012])[.](20[0-9][0-9]))|((29|30|31)[.](0[13578]|1[02])[.](20[0-9][0-9]))|((29|30)[.](0[469]|11)[.](20[0-9][0-9]))|(29[.]02[.](20(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))))")) {
			System.out.println("That's not a valid date of event!");
			scanner.nextLine();
		}
		newEvent.setDateOfEvent(scanner.nextLine());

		// time
		System.out.println("Enter starting time of event in format HH:MM: ");
		while (!scanner.hasNext("(((0|1)[0-9]|2[0-3])[:]([0-5][0-9]))")) {
			System.out.println("That's not a valid time of event!");
			scanner.nextLine();
		}
		newEvent.setTimeOfEvent(scanner.nextLine());

		// description
		System.out.println("Enter description of event: ");
		String description = scanner.nextLine();
		while (!validate("description", description)) {
			System.out.println("The description is too long, please keep it under 120 characters!");
			description = scanner.nextLine();
		}
		newEvent.setDescriptionOfEvent(description);

		System.out.println("Created event:\n" + newEvent.toString());
		scanner.reset();

		return newEvent;
	}

	/**
	 * This validates different fields of the event
	 * 
	 * @param field
	 *            this is the field that is going to be checked
	 * @param text
	 *            this is the text that is going to be validated
	 * @return true if the text is valid for the field and false if not
	 */
	
	public static boolean validate(String field, String text) {
		boolean isValid = false;
		switch (field) {
		case "type":
			isValid = Validator.validateType(text);
			break;
		case "marker":
			isValid = Validator.validateMarker(text);
			break;
		case "date":
			isValid = Validator.validateDate(text);
			break;
		case "time":
			isValid = Validator.validateTime(text);
			break;
		case "title":
			isValid = (text.length() > 0 && text.length() < 30);
			break;
		case "description":
			isValid = (text.length() > 0 && text.length() < 120);
		}
		return isValid;
	}

}
