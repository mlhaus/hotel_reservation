package api;

import model.*;
import service.*;
import utilities.*;
import java.util.*;

public class AdminResource {
    private static final RoomService roomService = RoomService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();

    public static void addRoom(Scanner scanner) {
        boolean addAnotherRoom = true;
        while(addAnotherRoom) {
            Room room = null;
            while(true) {
                try {
                    double pricePerNight = UserInput.getDouble(scanner, "Enter price per night", 0);
                    if(pricePerNight == 0) {
                        room = new FreeRoom();
                    } else {
                        room = new Room();
                    }
                    room.setPrice(Double.valueOf(pricePerNight));
                    break;
                } catch(IllegalArgumentException e) {
                    Helpers.displayMessage(e.getMessage());
                }
            }
            while(true) {
                try {
                    String roomNumber = UserInput.getString(scanner, "Enter room number"); //  (e.g. 101) or name (e.g. Suite A)
                    room.setRoomNumber(roomNumber);
                    break;
                } catch(IllegalArgumentException e) {
                    Helpers.displayMessage(e.getMessage());
                }
            }
            int roomType = UserInput.getInt(scanner, "Enter room type (1 for single bed, 2 for double bed)", 1, 2);
            room.setEnumeration(roomType == 1 ? RoomType.SINGLE : RoomType.DOUBLE);
            roomService.addRoom(room);
            addAnotherRoom = UserInput.getBoolean(scanner, "Would you like to add another room?");
        }
        Helpers.displayMessage("Finished adding rooms");
    }

    public static Collection<IRoom> getAllRooms() {
        return roomService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public static Collection<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    public static void addTestData() {
        int roomsAdded = 0;
        Room room1 = null;
        try {
            room1 = new FreeRoom("101", RoomType.SINGLE);
            roomService.addRoom(room1);
            roomsAdded++;
        } catch (IllegalArgumentException e) {
            room1 = (Room)roomService.getARoom("101");
        }
        Room room2 = null;
        try {
            room2 = new Room("100", Double.valueOf(100), RoomType.DOUBLE); // Was 1A
            roomService.addRoom(room2);
            roomsAdded++;
        } catch (IllegalArgumentException e) {
            room2 = (Room)roomService.getARoom("100");
        }

        int customersAdded = 0;
        Customer customer1 = null;
        try {
            customer1 = new Customer("John", "Doe", "john@example.com");
            customerService.addCustomer(customer1);
            customersAdded++;
        } catch (IllegalArgumentException e) {

        }
        Customer customer2 = null;
        try {
            customer2 = new Customer("Jane", "Doe", "jane@example.com");
            customerService.addCustomer(customer2);
            customersAdded++;
        } catch (IllegalArgumentException e) {

        }

        int reservationsAdded = 0;
        Date checkInDate = Helpers.now();
        Date checkOutDate = Helpers.addDays(checkInDate, 1);
        if(customer1 != null && room2 != null) {
            // Add a reservation, check in today, check out tomorrow
            customer1 = customerService.getCustomer("john@example.com");
            reservationService.reserveARoom(customer1, room2, checkInDate, checkOutDate);
            reservationsAdded++;
        }
        if(customer2 != null && room1 != null) {
            // Add a reservation in the past
            customer2 = customerService.getCustomer("jane@example.com");
            checkInDate = Helpers.addDays(Helpers.now(), -7);
            checkOutDate = Helpers.addDays(checkInDate, 1);
            reservationService.reserveARoom(customer2, room1, checkInDate, checkOutDate);
            reservationsAdded++;
        }

        Helpers.displayMessage(String.format("%d %s added", roomsAdded, roomsAdded == 1 ? "room" : "rooms"));
        Helpers.displayMessage(String.format("%d %s added", customersAdded, customersAdded == 1 ? "customer" : "customers"));
        Helpers.displayMessage(String.format("%d %s added", reservationsAdded, reservationsAdded == 1 ? "reservation" : "reservations"));
    }
}
