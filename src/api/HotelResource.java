package api;

import model.*;
import service.*;
import utilities.*;
import java.util.*;

public class HotelResource {
    private static final RoomService roomService = RoomService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();

    public static Customer getCustomer(String email) {
        Customer customer = customerService.getCustomer(email);
        return customer;
    }

    public static Customer createACustomer(Scanner scanner) {
        Helpers.displayMessage("Create a Customer Account");
        Customer customer = null;
        while(true) {
            String email = UserInput.getEmail(scanner);
            customer = getCustomer(email);
            if(customer != null) {
                if(UserInput.getBoolean(scanner, "A customer with that email already exists. Would you like to continue with that account?")) {
                    break;
                } else {
                    continue;
                }
            }
            customer = new Customer();
            customer.setEmail(email);
            while(true) {
                try {
                    String firstName = UserInput.getString(scanner, "First name");
                    customer.setFirstName(firstName);
                    break;
                } catch(IllegalArgumentException e) {
                    Helpers.displayMessage(e.getMessage());
                }
            }
            while(true) {
                try {
                    String lastName = UserInput.getString(scanner, "Last name");
                    customer.setLastName(lastName);
                    break;
                } catch(IllegalArgumentException e) {
                    Helpers.displayMessage(e.getMessage());
                }
            }
            customerService.addCustomer(customer);
            Helpers.displayMessage("Customer account created");
            break;
        }
        return customer;
    }

    public static IRoom getRoom(String roomNumber) {
        return roomService.getARoom(roomNumber);
    }

    public static void bookARoom(Scanner scanner, Map<String, IRoom> roomsAvailable, Date checkInDate, Date checkOutDate) {
        Helpers.displayMessage("Reserve a room");
        boolean hasAccount = UserInput.getBoolean(scanner, "Do you have an account with us?");
        Customer customer = null;
        if (!hasAccount) {
            customer = createACustomer(scanner);
        } else {
            customer = getCustomer(UserInput.getEmail(scanner));
            if(customer == null) {
                Helpers.displayMessage("No customer could not be found with that email. Please create an account before continuing.");
                return;
            }
        }
        String roomToReserve = UserInput.getString(scanner, "What room number would you like to reserve?").toUpperCase(); // 1A and 1a should match
        while(!roomsAvailable.containsKey(roomToReserve)) {
            Helpers.displayMessage("The room number you entered is not available or could not be found.");
            roomToReserve = UserInput.getString(scanner, "What room number would you like to reserve?").toUpperCase();
        }
        Room room = (Room)getRoom(roomToReserve);
        Reservation reservation = reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
        if(reservation != null) {
            System.out.printf(
                    "Reservation\n" +
                            "%s %s\n" +
                            "Room: %s - %s\n" +
                            "Price: %s price per night\n" +
                            "Check in Date: %s%n" +
                            "Check out Date: %s\n",
                    customer.getFirstName(), customer.getLastName(),
                    roomToReserve, room.getRoomType().getDescription(),
                    Helpers.toCurrency(room.getRoomPrice()),
                    Helpers.formatDate(checkInDate),
                    Helpers.formatDate(checkOutDate)
            );
        }
    }

    public static Collection<Reservation> getCustomersReservations(Scanner scanner) {
        Customer customer = getCustomer(UserInput.getEmail(scanner));
        if(customer == null) {
            return null;
        }
        Set<Reservation> reservations = (Set<Reservation>)reservationService.getCustomersReservation(customer);
        return reservations;
    }

    public static void findARoom(Scanner scanner) {
        if(roomService.getAllRooms().size() == 0) {
            Helpers.displayMessage("There are no rooms in the system");
            return;
        }
        Helpers.displayMessage("Find a room");

        Date checkInDate = null;
        while(true) {
            checkInDate = UserInput.getDate(scanner, "Enter check in date");
            if(Helpers.isDateInTheFuture(checkInDate)) {
                break;
            } else {
                Helpers.displayMessage("Check in date cannot be in the past.");
            }
        }
        Date checkOutDate = null;
        while(true) {
            checkOutDate = UserInput.getDate(scanner, "Enter check out date");
            if(Helpers.areDatesInOrder(checkInDate, checkOutDate)) {
                break;
            } else {
                Helpers.displayMessage("Check out date must come after the check in date.");
            }
        }
        Map<String, IRoom> roomsAvailable = reservationService.findRooms(checkInDate, checkOutDate);
        if (roomsAvailable.size() == 0) {
            Helpers.displayMessage("Sorry, there are no rooms available");
            checkInDate = Helpers.addDays(checkInDate, 7);
            checkOutDate = Helpers.addDays(checkOutDate, 7);
            boolean add7Days = UserInput.getBoolean(scanner, String.format("Would you like to see availability from %s to %s?", Helpers.formatDate(checkInDate), Helpers.formatDate(checkOutDate)));
            if(add7Days) {
                roomsAvailable = reservationService.findRooms(checkInDate, checkOutDate);
                if (roomsAvailable.size() == 0) {
                    Helpers.displayMessage("Sorry, there are still no rooms available.");
                    return;
                }
            } else {
                return;
            }
        }
        if (UserInput.getBoolean(scanner, "Would you like to book a room?")) {
            bookARoom(scanner, roomsAvailable, checkInDate, checkOutDate);
        } else {
            Helpers.displayMessage("Find and reserve room function ended");
        }
    }
}