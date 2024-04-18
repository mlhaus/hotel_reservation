package model;

import utilities.*;
import java.util.*;

public class Reservation implements Cloneable, Comparable<Reservation> {
    private Customer customer;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation() {
        this(new Customer(), new Room(), Helpers.now(), Helpers.addDays(Helpers.now(), 1));
    }

    public Reservation(Customer customer, Room room, Date checkInDate, Date checkOutDate) {
        setCustomer(customer);
        setRoom(room);
        setCheckInDate(checkInDate);
        setCheckOutDate(checkOutDate);
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCustomer(Customer customer) {
        if(customer == null) {
            throw new IllegalArgumentException("Customer required");
        }
        this.customer = customer;
    }

    public void setRoom(Room room) {
        if(room == null) {
            throw new IllegalArgumentException("Room required");
        }
        this.room = room;
    }

    public void setCheckInDate(Date checkInDate) {
        if(checkInDate == null) {
            throw new IllegalArgumentException("Check in date required");
        }
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        if(checkOutDate == null) {
            throw new IllegalArgumentException("Check out date required");
        }
        this.checkOutDate = checkOutDate;
    }

    public static void printReservationHeader() {
        System.out.printf("%-20s%-20s%-13s%-18s%-18s\n", "First Name", "Last Name", "Room Number", "Check In", "Check Out");
    }

    @Override
    public String toString() {
        return String.format("%-20s%-20s%-13s%-18s%-18s", customer.getFirstName(), customer.getLastName(),
                room.getRoomNumber(),
                Helpers.dateOutputFormat.format(checkInDate), Helpers.dateOutputFormat.format(checkOutDate));
    }

    @Override
    public Reservation clone() {
        try{
            return (Reservation) super.clone();
        } catch(CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(Reservation o) {
        int result = this.checkInDate.compareTo(o.checkInDate);
        if(result == 0) {
            result = this.checkOutDate.compareTo(o.checkOutDate);
        }
        if(result == 0) {
            result = this.room.compareTo(o.room);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Reservation)obj) == 0;
    }
}
