package service;

import model.*;
import java.util.*;

public final class RoomService {
//    private static Map<String, IRoom> rooms = new TreeMap<>();
    private static Set<IRoom> rooms = new TreeSet<>();
    private static RoomService INSTANCE;

    private RoomService() {

    }

    public static RoomService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new RoomService();
        }
        return INSTANCE;
    }

    public static void addRoom(IRoom room) {
        // toUpperCase is used so "1a" and "1A" are the same.
//        if(!rooms.containsKey(room.getRoomNumber().toUpperCase())) {
//            rooms.put(room.getRoomNumber().toUpperCase(), room);
//        }
        if(!rooms.contains(room)) {
            rooms.add(room);
        }
    }
    public static IRoom getARoom(String roomId) {
        // toUpperCase is used so "1a" and "1A" are the same.
//        return rooms.get(roomId.toUpperCase());
        try{
            // Source: https://stackoverflow.com/a/38485455
            return rooms.stream().filter(room->room.getRoomNumber().equalsIgnoreCase(roomId)).findFirst().get();
        } catch(NoSuchElementException e) {
            return null;
        }
    }
    public static Collection<IRoom> getAllRooms() {
//        return new ArrayList<>(rooms.values());
        return rooms;
    }
}
