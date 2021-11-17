package model;

import java.util.*;

public class Room implements IRoom {
    private final String roomNumber;
    protected final Double price;
    private final RoomType roomType;
    private final boolean isFree;

    //Constructor
    public Room(String roomNumber, Double price, RoomType roomType, boolean isFree) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
        this.isFree = isFree;
    }

    //Getter Methods
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
        return roomType;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Room room = (Room) obj;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }


    @Override
    public String toString() {
        return "RoomNumber:" + roomNumber + " " + "price:" + price + " " + "RoomType:" + roomType;
    }

}