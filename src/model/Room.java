package model;

import service.*;
import utilities.*;

public class Room implements IRoom, Comparable<Room> {
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room() {
        this("0", Double.valueOf(0), RoomType.SINGLE);
    }

    public Room(String roomNumber, Double price, RoomType enumeration) {
        setRoomNumber(roomNumber);
        setPrice(price);
        setEnumeration(enumeration);
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    public void setRoomNumber(String roomNumber) {
        if(!Helpers.validString(roomNumber)) {
            throw new IllegalArgumentException("Room number required");
        } else if(!Helpers.isNumeric(roomNumber)) {
            throw new IllegalArgumentException("Room number cannot contain non-numeric characters");
        } else if(roomNumber.length() > 10) {
            throw new IllegalArgumentException("Room number cannot have more than 10 characters");
        }
        else if(RoomService.getInstance().getARoom(roomNumber) != null) {
            throw new IllegalArgumentException("That room number already exists");
        }
        this.roomNumber = roomNumber; // 1A and 1a should be the same;
    }

    public void setPrice(Double price) {
        if(price == null || price < 0) {
            throw new IllegalArgumentException("Price cannot be less than 0");
        }
        this.price = price;
    }

    public void setEnumeration(RoomType enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public boolean isFree() {
        return price == 0;
    }

    public static void printRoomHeader() {
        System.out.printf("%-11s%18s  %-12s\n", "Room", "Price Per Night", "Type");
    }

    @Override
    public String toString() {
        return String.format("%-11s%18s  %-12s", roomNumber, Helpers.toCurrency(price), enumeration.getDescription());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Room)obj) == 0;
    }

    @Override
    public int compareTo(Room o) {
        return this.roomNumber.compareToIgnoreCase(o.roomNumber);
    }
}
