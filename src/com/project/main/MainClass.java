package com.project.main;

import java.sql.SQLException;
import java.util.Scanner;

import com.project.database.Organizer;
import com.project.event.Creator;
import com.project.event.Event;
import com.project.hashMap.hashMapHandler;

/**
 * This is the main menu for the program, which calls different operations depending on the choice of the user
 * 
 * @author kate
 *
 */

public class MainClass {
	
	public static void main(String[] args) throws SQLException {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Organizer organizer = new Organizer();
		String title = "";
		System.out.println("Welcome to your personal Event Organizer.");

		while (true) {

			System.out.println(
					"\nPlease choose an option: \n\t1 - Add a new event;\n\t2 - Update an existing event;\n\t3 - Delete an existing event;\n\t4 - Display events;\n\t5 - Exit the Organizer");
			while (!scanner.hasNextInt()) {
				System.out.println("That's not a valid choice!");
				scanner.nextLine();
			}
			int option = scanner.nextInt();
			
			
			switch (option) {
			case 1:
				Event event = Creator.createEvent();
				organizer.addEvent(event);
				break;
			case 2:
				System.out.println("Enter the title of the event you want to update: ");
				scanner.nextLine();
				title = scanner.nextLine();
				organizer.findEvent(title); 
				hashMapHandler.mapHandler(1);			
				break;
			case 3:
				System.out.println("Enter the title of the event you want to delete: ");
				scanner.nextLine();
				title = scanner.nextLine();
				organizer.findEvent(title); 
				hashMapHandler.mapHandler(2);	
				break;
			case 4:
				System.out.println("Choose which events to be displayed: \n\t1 - For a particular day;\n\t2 - For a particular month;\n\t3 - For a particular year;\n\t4 - All events;\n\t0 - Return to main menu.");
				int choice = scanner.nextInt();
				while (choice < 0 || choice > 4) {
					System.out.println("That's not a valid choice!");
					choice = scanner.nextInt();
				}
				organizer.dateHandler(choice);
				break;
			case 5:
				System.out.println("You have exited the Event Organizer.");
				return;
			default:
				System.out.println("Not a valid choice..");
			}
		}
	}
}
