package model;

public class FreeRoom extends Room {
    public FreeRoom (String roomNumber, RoomType roomType, boolean isFree) {
        super(roomNumber, 0.0, roomType, true);
    }

    @Override
    public String toString() {
        return "This is a free Room";
    }
}
