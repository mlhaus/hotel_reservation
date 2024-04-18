package service;

import model.*;
import utilities.*;
import java.util.*;

public final class ReservationService {
    private static Set<Reservation> reservations = new TreeSet<>();
    private static ReservationService INSTANCE;

    private ReservationService() {

    }

    public static ReservationService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }

    public static Reservation reserveARoom(Customer customer, Room room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = null;
        if(isRoomAvailable(room, checkInDate, checkOutDate)) {
            try {
                reservation = new Reservation(customer, room, checkInDate, checkOutDate);
                reservations.add(reservation);
            } catch(IllegalArgumentException e) {
                Helpers.displayMessage(e.getMessage());
            }
        } else {
            Helpers.displayMessage("The room you tried to reserve is not available for the given dates");
        }
        return reservation;
    }

    public static boolean doDateRangesOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return (startDate1.compareTo(endDate2) < 0) && (startDate2.compareTo(endDate1) < 0);
    }

    private static Set<Reservation> deepCopy() {
        Set<Reservation> reservationsCopy = new TreeSet<>();
        for(Reservation reservation : reservations) {
            reservationsCopy.add(reservation.clone());
        }
        return reservationsCopy;
    }

    public static boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        Set<Reservation> reservationsCopy = deepCopy();
        // Remove reservations that aren't the room specified
        reservationsCopy.removeIf(reservation -> !reservation.getRoom().getRoomNumber().equals(room.getRoomNumber()));
        // Remove reservations that aren't in the specified date range.
        reservationsCopy.removeIf(reservation -> !doDateRangesOverlap(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate()));
        return reservationsCopy.size() == 0; // room is available if there are no matches
    }

    public static Map<String, IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Helpers.displayMessage(String.format("Showing all rooms and their availability from %s to %s.", Helpers.formatDate(checkInDate), Helpers.formatDate(checkOutDate)));
        Map<String, IRoom> roomsAvailable = new HashMap<>();
        System.out.printf("%-13s", "Available");
        Room.printRoomHeader();
        for(IRoom room: RoomService.getInstance().getAllRooms()) {
            if(isRoomAvailable(room, checkInDate, checkOutDate)) {
                System.out.printf("%-13s", "Yes");
                roomsAvailable.put(room.getRoomNumber(), room);
            } else {
                System.out.printf("%-13s", "No");
            }
            System.out.println(room);
        }
        return roomsAvailable;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        Set<Reservation> reservationsCopy = deepCopy();
        // Remove reservations that aren't from the specified customer
        reservationsCopy.removeIf(reservation -> !reservation.getCustomer().equals(customer));
        return reservationsCopy;
    }

    public static Collection<Reservation> getAllReservations() {
        return reservations;
    }

//    Here I was testing the doDateRangesOverlap and isRoomAvailable methods
//    public static void main(String[] args) throws ParseException {
//        Date startDate1 = Helpers.dateFormatFromUser.parse("5/28/2023");
//        Date endDate1 = Helpers.dateFormatFromUser.parse("5/30/2023");
//        Date startDate2 = Helpers.dateFormatFromUser.parse("5/26/2023");
//        Date endDate2 = Helpers.dateFormatFromUser.parse("5/27/2023");
//        System.out.println(doDateRangesOverlap(startDate1, endDate1, startDate2, endDate2)); // false
//        startDate2 = Helpers.dateFormatFromUser.parse("5/27/2023");
//        endDate2 = Helpers.dateFormatFromUser.parse("5/28/2023");
//        System.out.println(doDateRangesOverlap(startDate1, endDate1, startDate2, endDate2)); // false
//        startDate2 = Helpers.dateFormatFromUser.parse("5/28/2023");
//        endDate2 = Helpers.dateFormatFromUser.parse("5/29/2023");
//        System.out.println(doDateRangesOverlap(startDate1, endDate1, startDate2, endDate2)); // true
//        startDate2 = Helpers.dateFormatFromUser.parse("5/29/2023");
//        endDate2 = Helpers.dateFormatFromUser.parse("5/30/2023");
//        System.out.println(doDateRangesOverlap(startDate1, endDate1, startDate2, endDate2)); // true
//        startDate2 = Helpers.dateFormatFromUser.parse("5/30/2023");
//        endDate2 = Helpers.dateFormatFromUser.parse("5/31/2023");
//        System.out.println(doDateRangesOverlap(startDate1, endDate1, startDate2, endDate2)); // false
//        startDate2 = Helpers.dateFormatFromUser.parse("5/31/2023");
//        endDate2 = Helpers.dateFormatFromUser.parse("6/1/2023");
//        System.out.println(doDateRangesOverlap(startDate1, endDate1, startDate2, endDate2)); // false

//        IRoom room1 = new Room("101", Double.valueOf(100), RoomType.SINGLE);
//        IRoom room2 = new Room("102", Double.valueOf(120), RoomType.DOUBLE);
//        Customer customer1 = new Customer("John", "Doe", "john@example.com");
//        Reservation reservation1 = new Reservation(customer1, room1, Helpers.dateFormatFromUser.parse("5/18/2023"), Helpers.dateFormatFromUser.parse("5/23/2023"));
//        Reservation reservation2 = new Reservation(customer1, room1, Helpers.dateFormatFromUser.parse("5/25/2023"), Helpers.dateFormatFromUser.parse("5/30/2023"));
//        reservations.add(reservation1);
//        reservations.add(reservation2);
//
//        Date checkInDate = Helpers.dateFormatFromUser.parse("5/28/2023");
//        Date checkOutDate = Helpers.dateFormatFromUser.parse("6/1/2023");
//        System.out.println(isRoomAvailable(room1, checkInDate, checkOutDate)); // false
//        System.out.println(isRoomAvailable(room2, checkInDate, checkOutDate)); // true
//    }
}
