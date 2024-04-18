package api;

import model.*;
import utilities.*;
import java.util.*;

public class AdminMenu implements Menu{
    @Override
    public void prompt(Scanner scanner) {
        while(true) {
            System.out.println("\nAdmin Menu");
            String[] menuItems = {
                    "See all Customers",
                    "See all Rooms",
                    "See all Reservations",
                    "Add a Room",
                    "Add Test Data",
                    "Back to Main Menu "
            };
            printMenuItems(menuItems);
            int choice = UserInput.getInt(scanner, "Please select a number for the menu option", 1, menuItems.length);
            switch (choice) {
                case 1:
                    List<Customer> customers = (List<Customer>)AdminResource.getAllCustomers();
                    if(customers.size() == 0) {
                        Helpers.displayMessage("There are no customers in the system");
                    } else {
                        Helpers.displayMessage("Showing all customers, sorted by Email");
                        Customer.printCustomerHeader();
                        for (Customer customer : customers) {
                            System.out.println(customer);
                        }
                    }
                    break;
                case 2:
                    Set<IRoom> rooms = (Set<IRoom>)AdminResource.getAllRooms();
                    if(rooms.size() == 0) {
                        Helpers.displayMessage("There are no rooms in the system");
                    } else {
                        Helpers.displayMessage("Showing all rooms, sorted alphabetically by Room");
                        Room.printRoomHeader();
                        for (IRoom room : rooms) {
                            System.out.println(room);
                        }
                    }
                    break;
                case 3:
                    Set<Reservation> reservations = (Set<Reservation>)AdminResource.getAllReservations();
                    if(reservations.size() == 0) {
                        Helpers.displayMessage("There are no reservations in the system");
                    } else {
                        Helpers.displayMessage("Showing all reservations, sorted by Check In date");
                        Reservation.printReservationHeader();
                        for (Reservation reservation : reservations) {
                            System.out.println(reservation);
                        }
                    }
                    break;
                case 4:
                    Helpers.displayMessage("Add a Room");
                    AdminResource.addRoom(scanner);
                    break;
                case 5:
                    AdminResource.addTestData();
                    break;
                case 6:
                    Helpers.displayMessage("Going back to the main menu");
                    return;
            }
            Helpers.pressEnterToContinue(scanner);
        }
    }
}
