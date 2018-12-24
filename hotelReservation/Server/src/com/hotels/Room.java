package com.hotels;

import java.io.Serializable;


//

// A room has a unique id number across all hotels and it has a number which is unique across the hotel itself.
//the cost of the room depends on the type


public class Room extends UniqueID implements Serializable {



    public enum RoomStatus {
        EMPTY, OCCUPIED

    }

    private RoomType type;
    private RoomStatus status;

    private int number;



    public Room(RoomType type, int number) {

        super();

            this.type = type;
            this.number = number;
            this.status = RoomStatus.EMPTY;


    }

    public boolean isEmpty(Room room) {
        return room.status == RoomStatus.EMPTY;
    }

    public int getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }



    @Override
    public String toString() {

        return "ID=" + getId() + "\t"
                + "Room nr=" + getNumber() + "\t"
                + "Type=" + getType() + "\t"
                + "Status=" + getStatus() + "\n";
    }



}
