package api;

import model.*;
import utilities.*;
import java.util.*;

public class MainMenu implements Menu {
    @Override
    public void prompt(Scanner scanner) {
        while(true) {
            System.out.println("\nWelcome to the Hotel Reservation Application");
            String[] menuItems = {
                    "Find and reserve a room",
                    "See my reservations",
                    "Create an account",
                    "Admin",
                    "Exit"
            };
            printMenuItems(menuItems);
            int choice = UserInput.getInt(scanner, "Please select a number for the menu option", 1, menuItems.length);
            switch (choice) {
                case 1:
                    HotelResource.findARoom(scanner);
                    break;
                case 2:
                    Set<Reservation> reservations = (Set<Reservation>)HotelResource.getCustomersReservations(scanner);
                    if(reservations == null) {
                        Helpers.displayMessage("No customer could not be found with that email.");
                    } else if(reservations.size() == 0) {
                        Helpers.displayMessage("There are no reservations for that customer");
                    } else {
                        Helpers.displayMessage("Showing customer's reservations, sorted by Check In date");
                        Reservation.printReservationHeader();
                        for (Reservation reservation : reservations) {
                            System.out.println(reservation);
                        }
                    }
                    break;
                case 3:
                    HotelResource.createACustomer(scanner);
                    break;
                case 4:
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.prompt(scanner);
                    break;
                case 5:
                    return;
            }
            Helpers.pressEnterToContinue(scanner);
        }
    }
}
