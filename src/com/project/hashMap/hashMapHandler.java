package com.project.hashMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;

import com.project.database.Organizer;
import com.project.event.*;
import com.project.event.Type;
import com.project.main.MainClass;

public class hashMapHandler {
	public static HashMap<Integer, Event> myMap = new HashMap<>();
	public static HashMap<Integer, Integer> ids = new HashMap<>();

	/**
	 * creates an event from the information in the ResultSet, adds it to the
	 * hashMap and displays it to the user
	 * 
	 * @param myRs
	 *            this is the ResultSet from the performed search in the DB
	 */

	public static void createHashMap(ResultSet myRs) throws SQLException {
		while (myRs.next()) {
			Event e = new Event(myRs.getString("title"), null, null,
					myRs.getString("day") + "." + myRs.getString("month") + "." + myRs.getString("year"),
					myRs.getString("time"), myRs.getString("description"));

			if (myRs.getString("type").equals("TASK")) {
				e.setTypeOfEvent(Type.TASK);
			} else
				e.setTypeOfEvent(Type.MEETING);

			if (myRs.getString("marker").equals("PUBLIC")) {
				e.setMarkerOfEvent(Marker.PUBLIC);
			} else if (myRs.getString("marker").equals("PRIVATE")) {
				e.setMarkerOfEvent(Marker.PRIVATE);
			} else
				e.setMarkerOfEvent(Marker.CONFIDENTIAL);

			myMap.put(Integer.parseInt(myRs.getString("id")), e);
		}
		printMap(myMap);
	}

	/**
	 * displays the events in the map
	 * 
	 * @param map
	 *            this is the map of events that has been created
	 */

	public static void printMap(HashMap<Integer, Event> map) {
		int i = 1;
		if (map.isEmpty())
			return;
		Iterator<Entry<Integer, Event>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry<Integer, Event> pair = (HashMap.Entry<Integer, Event>) it.next();
			System.out.println(i + " => " + pair.getValue());
			ids.put(i, pair.getKey());
			i++;
		}
	}

	/**
	 * Prompts user to choose an event, calls chooseField(), gets new
	 * information from the user and calls updateEvent()/deleteEvent(). Clears
	 * all hashMaps after all called methods are performed.
	 * 
	 * @param n
	 *            this indicates if an event will be updated or deleted
	 */

	public static void mapHandler(int n) throws SQLException {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int selectedEvent = 1;

		if (myMap.isEmpty()) {
			System.out.println("No events found!");
			return;
		}

		switch (n) {
		case 1:
			String chosenField = "";

			if (myMap.size() == 1) {
				chosenField = chooseField();
			} else {
				System.out.println("Enter the number of the event you want to update: ");
				selectedEvent = scanner.nextInt();
				while (selectedEvent <= 0 || selectedEvent > myMap.size()) {
					System.out.println("That's not a valid number of event!");
					selectedEvent = scanner.nextInt();
				}
				chosenField = chooseField();
				scanner.nextLine();
			}

			System.out.println("Enter new info: ");
			String updatedInfo = scanner.nextLine();

			if (Creator.validate(chosenField, updatedInfo)) {
				Organizer.updateEvent(ids.get(selectedEvent), chosenField, updatedInfo);
			} else {
				System.out.println("The information you provided is not valid for the chosen field.");
			}
			myMap.clear();
			ids.clear();
			break;

		case 2:
			if (myMap.size() == 1) {
				Organizer.deleteEvent(ids.get(selectedEvent));
			} else {
				System.out.println("Enter the number of the event you want to delete: ");
				selectedEvent = scanner.nextInt();
				while (selectedEvent <= 0 || selectedEvent > myMap.size()) {
					System.out.println("That's not a valid number of event!");
					selectedEvent = scanner.nextInt();
				}
				Organizer.deleteEvent(ids.get(selectedEvent));
			}
			myMap.clear();
			ids.clear();
			break;
		}
	}

	/**
	 * creates an event from the information in the ResultSet, adds it to the
	 * hashMap and displays it to the user
	 * 
	 * @return String this is the name of the field that will be updated in the
	 *         DB
	 */

	public static String chooseField() throws SQLException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int field = 0;
		System.out.println(
				"\nChoose which field you want to update: \n\t1 - Title;\n\t2 - Type;\n\t3 - Marker;\n\t4 - Date;\n\t5 - Time;\n\t6 - Description;\n\t0 - Return to main menu.");
		field = scanner.nextInt();
		while (field < 0 || field > 6) {
			System.out.println("That's not a valid choice!");
			field = scanner.nextInt();
		}
		if (field == 0) {
			myMap.clear();
			ids.clear();
			MainClass.main(null);
		}
		System.out.println("You chose to update the " + transform(field) + " of the event.");
		return transform(field);
	}

	/**
	 * Helper method - transforms a given number to a valid string, field in the
	 * DB
	 * 
	 * @param i
	 *            this is the choice made from the user
	 * @return String this is the name of the field in the DB
	 */

	public static String transform(int i) {
		String transformed = "";
		switch (i) {
		case 1:
			transformed = "title";
			break;
		case 2:
			transformed = "type";
			break;
		case 3:
			transformed = "marker";
			break;
		case 4:
			transformed = "date";
			break;
		case 5:
			transformed = "time";
			break;
		case 6:
			transformed = "description";
			break;
		}
		return transformed;
	}
}
