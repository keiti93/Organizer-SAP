package com.project.database;

import java.sql.*;
import java.util.Scanner;

import com.project.event.*;
import com.project.graphical.consoleOutput;
import com.project.hashMap.hashMapHandler;

public class Organizer {

	/**
	 * Adds a new event to the DB
	 * 
	 * @param e
	 *            this is the event that will be added to the DB
	 */

	public void addEvent(Event e) {

		try (Connection myCon = DriverManager.getConnection(DBinfo.url, DBinfo.user, DBinfo.password);
				Statement myStmt = myCon.createStatement();) {

			String sql = "INSERT INTO events " + "(title, type, marker, day, month, year, time, description)"
					+ " VALUES ('" + e.getTitleOfEvent() + "', '" + e.getTypeOfEvent() + "', '" + e.getMarkerOfEvent()
					+ "', '" + e.getDateOfEvent().substring(0, 2) + "', '" + e.getDateOfEvent().substring(3, 5) + "', '"
					+ e.getDateOfEvent().substring(6) + "', '" + e.getTimeOfEvent() + "', '" + e.getDescriptionOfEvent()
					+ "')";

			myStmt.executeUpdate(sql);
			System.out.println("Event added to Organizer.");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Finds an existing event in the DB
	 * 
	 * @param title
	 *            this is the title of the event that has to be found
	 */

	public void findEvent(String title) throws SQLException {
		try (Connection myCon = DriverManager.getConnection(DBinfo.url, DBinfo.user, DBinfo.password);
				PreparedStatement myStmt = myCon.prepareStatement("select * from organizerDB.events where title=?");) {

			((PreparedStatement) myStmt).setString(1, title);
			System.out.println("Searching for " + title + " . . .");
			ResultSet myRs = myStmt.executeQuery();
			hashMapHandler.createHashMap(myRs);

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Displays the events in the map with different colors
	 * 
	 * @param myRS
	 *            this is the ResultSet from the performed search in the DB
	 */

	private static void display(ResultSet myRs) throws SQLException {
		int i = 1;
		if (myRs.wasNull()) {
			System.out.println("No events on this date!");
		}
		while (myRs.next()) {
			String title = myRs.getString("title");
			String type = myRs.getString("type");
			String marker = myRs.getString("marker");
			String date = myRs.getString("day") + "." + myRs.getString("month") + "." + myRs.getString("year");
			String time = myRs.getString("time");
			String description = myRs.getString("description");
			String result = "%2d: %-30s on\t %10s \tat\t%5s \t%-7s   %-12s \tDescription: %s \n";

			if (type.equals("TASK")) {
				System.out.printf(consoleOutput.ANSI_CYAN + result, i, title, date, time, type, marker,
						description + consoleOutput.ANSI_RESET);
			} else {

				System.out.printf(consoleOutput.ANSI_PURPLE + result, i, title, date, time, type, marker,
						description + consoleOutput.ANSI_RESET);
			}
			i++;
		}
	}

	/**
	 * Updates a chosen event in the DB
	 * 
	 * @param chosenEvent
	 *            this is the id of the event that has to be updated
	 * @param chosenField
	 *            this is the field that is going to be updated in the DB
	 * @param newData
	 *            this is the new data that is going to be updated in the DB
	 */

	public static void updateEvent(int chosenEvent, String chosenField, String newData) throws SQLException {

		PreparedStatement prepStmt = null;
		try (Connection myCon = DriverManager.getConnection(DBinfo.url, DBinfo.user, DBinfo.password);) {
			if (chosenField.equals("date")) {
				chosenField = "day=?, month=?, year";

				String sql = "UPDATE organizerDB.events SET " + chosenField + "=? where id=?";
				prepStmt = myCon.prepareStatement(sql);
				prepStmt.setString(1, newData.substring(0, 2));
				prepStmt.setString(2, newData.substring(3, 5));
				prepStmt.setString(3, newData.substring(6));
				prepStmt.setInt(4, chosenEvent);
			} else {
				if (chosenField.equals("type") || chosenField.equals("marker")) {
					newData = newData.toUpperCase();
				}
				String sql = "UPDATE organizerDB.events SET " + chosenField + "=? where id=?";
				prepStmt = myCon.prepareStatement(sql);
				prepStmt.setString(1, newData);
				prepStmt.setInt(2, chosenEvent);
			}
			prepStmt.executeUpdate();
			
			String sql2 = "SELECT title, day, month, year FROM organizerDB.events";
			prepStmt.executeQuery(sql2);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (prepStmt != null) {
				prepStmt.close();
			}
			System.out.println("Your event has been updated!");
		}
	}

	/**
	 * Deletes an existing event in the DB
	 * 
	 * @param id
	 *            this is the id of the event that has to be deleted
	 */

	public static void deleteEvent(int id) {

		try (Connection myCon = DriverManager.getConnection(DBinfo.url, DBinfo.user, DBinfo.password);
				Statement myStmt = myCon.createStatement();) {

			String sql = "DELETE from organizerDB.events where id='" + id + "'";
			myStmt.executeUpdate(sql);
			System.out.println("Event deleted.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Finds events for particular date (day, month or year) in the DB and displays them via display()
	 * 
	 * @param i
	 *            this indicates if the user wants the events for a particular day(1), month(2) or year(3)
	 * @param date
	 *            this is the date 
	 */

	public static void listEvents(int i, String date) {
		try (Connection myCon = DriverManager.getConnection(DBinfo.url, DBinfo.user, DBinfo.password);
				Statement myStmt = myCon.createStatement();) {
			String sql = "";
			ResultSet rs = null;
			
			switch (i) {
			case 1:
				sql = "SELECT * FROM organizerDB.events where day='" + date.substring(0, 2) + "' AND month='"
						+ date.substring(3, 5) + "'AND year='" + date.substring(6) + "' " + "ORDER BY time ASC";

				rs = myStmt.executeQuery(sql);
				display(rs);
				break;
			case 2:
				sql = "SELECT * FROM organizerDB.events where month='" + date.substring(0, 2) + "' AND year='"
						+ date.substring(3) + "' " + " ORDER BY day ASC, time ASC";
				rs = myStmt.executeQuery(sql);
				display(rs);
				break;
			case 3:
				sql = "SELECT * FROM organizerDB.events where year='" + date + "' "
						+ " ORDER BY month ASC, day ASC, time ASC";
				rs = myStmt.executeQuery(sql);
				display(rs);
				break;
			case 4:
				sql = "SELECT * FROM organizerDB.events"
						+ " ORDER BY year ASC, month ASC, day ASC, time ASC";
				rs = myStmt.executeQuery(sql);
				display(rs);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Helper method - takes input from the user about the date and validates it, then calls listEvents()
	 * 
	 * @param choice
	 *            this indicates if the user wants the events for a particular day(1), month(2) or year(3)
	 */
	
	public void dateHandler(int choice) {
		String date = "";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		switch (choice) {
		case 1:
			System.out.println("Enter a date in format DD.MM.YYYY:");
			while (!scanner.hasNext(
					"(((0[1-9]|1[0-9]|2[0-8])[.](0[1-9]|1[012])[.](20[0-9][0-9]))|((29|30|31)[.](0[13578]|1[02])[.](20[0-9][0-9]))|((29|30)[.](0[469]|11)[.](20[0-9][0-9]))|(29[.]02[.](20(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))))")) {
				System.out.println("That's not a valid date!");
				scanner.nextLine();
			}
			date = scanner.nextLine();
			listEvents(1, date);
			break;
		case 2:
			System.out.println("Enter a month in format MM.YYYY:");
			while (!scanner.hasNext("(0[1-9]|1[012])[.](20[0-9][0-9])")) {
				System.out.println("That's not a valid date!");
				scanner.nextLine();
			}
			date = scanner.nextLine();
			listEvents(2, date);
			break;
		case 3:
			System.out.println("Enter a year in format YYYY:");
			while (!scanner.hasNext("(20[0-9][0-9])")) {
				System.out.println("That's not a valid date!");
				scanner.nextLine();
			}
			date = scanner.nextLine();
			listEvents(3, date);
			break;
		case 4:
			listEvents(4, date);
		}
	}
}
