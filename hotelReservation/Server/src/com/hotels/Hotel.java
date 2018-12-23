package com.hotels;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Each hotel has a unique id, name, address and rooms that can be reserved by guests.
//first of all, the hotel must be created and then adding rooms to it.

public class Hotel extends Location implements Serializable {
    


    private ArrayList<Room> rooms = new ArrayList<>();



    public Hotel(String name, String address) {
	super(name, address);
    }



	//To add a room, you have to have the type of the room, its number and the cost.
    public int addRoom(RoomType.Type type, int number, double price)  {
	

	
	Room room = null;
	switch (type) {
	case Single:
	    room = new Room(new Single(), number);
	    break;
	case Double:
	    room = new Room(new Double(), number);
	    break;
	case Family:
	    room = new Room(new Family(), number);
	    break;
	case Suite:
	    room = new Room(new Suite(), number);
	    break;
	default:
		try {
			throw new IOException("Unknown type of room: " + type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	rooms.add(room);
	return room.getId();
    }

	public ArrayList<Room> getRooms() {
		return rooms;
	}

// getting a room by its number
	public Room getRoom(int roomNumber) {
	


		try {
			for (Room room : rooms)
				if (room.getNumber() == roomNumber)
				return room;
		} catch (Exception e) {
			System.out.println("Room was not found: " + roomNumber);
			e.printStackTrace();
		}
		return null;
    }



		@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\n" + getName() + " Hotel{\n");
		sb.append("        ID number= ").append(getId()).append("\n");
		sb.append("        Name = ").append(getName()).append("\n");
		sb.append("        Address = ").append(getAddress()).append("\n");
		sb.append("        Rooms = ").append(getRooms());
		sb.append('}');
//		sb.append("\n");
		return sb.toString();
	}
}
