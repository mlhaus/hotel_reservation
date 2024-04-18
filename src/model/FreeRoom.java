package model;

import utilities.Helpers;

public class FreeRoom extends Room{

    public FreeRoom() {
        this("Undefined", RoomType.SINGLE);
    }

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, Double.valueOf(0), enumeration);
    }

    @Override
    public String toString() {
        return String.format("%-11s%18s  %-12s", super.getRoomNumber(), "Free", super.getRoomType().getDescription());
    }
}
