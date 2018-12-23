package com.hotels;

import java.io.IOException;
import java.util.ArrayList;




//A booking system has all the information about hotels,
// guest and room bookings. Hotels in reality use numeric references
//to reference hotels, rooms, bookings and guest.

public class BookingSystem {

	private ArrayList<Guest> guests = new ArrayList<>();

	private ArrayList<Hotel> hotels = new ArrayList<>();

	// Current bookings are those that have been made, but whose
	// guests have yet to check-in and pay...

	private ArrayList<Booking> current = new ArrayList<>();

	// A paid booking is moved from the current bookings to the
	// history so that the booking system knows about all past
	// bookings...

	private ArrayList<Booking> history = new ArrayList<>();

	public BookingSystem() {
	}


	public ArrayList<Booking> getCurrent() {
		return current;
	}

	public ArrayList<Guest> getGuests() {
		return guests;
	}



	public ArrayList<Booking> getHistory() {
		return history;
	}

	public ArrayList<Hotel> getHotels() {
		return hotels;
	}


	public void setCurrent(ArrayList<Booking> current) {
		this.current = current;
	}

	public void setGuests(ArrayList<Guest> guests) {
		this.guests = guests;
	}

	public void setHistory(ArrayList<Booking> history) {
		this.history = history;
	}

	public void setHotels(ArrayList<Hotel> hotels) {
		this.hotels = hotels;
	}




	public int addHotel(String name, String address)  throws IOException{


			if(!hotelExist(name, address)) {
				Hotel hotel = new Hotel(name, address);

				hotels.add(hotel);
				return hotel.getId();
			}

		throw new IOException("Hotel exists");

	}



	public String displayHotels() throws IOException{
		final StringBuilder sb;
		if (hotels != null) {
			sb = new StringBuilder();

			for (Hotel hotel : hotels) {
				sb.append(displayRoomsByHotel(hotel.getName(), hotel.getAddress()));
				sb.append("\n");

			}
		} else return "";

		return sb.toString();
	}


	public String searchHotelByCity(String city) {
		String hotel="";
		if (city != null) {
			for (Hotel hot : hotels) {
				if (hot.getAddress().equalsIgnoreCase(city)) {
					hotel = hotel + hot.toString();
				}
			}

		}else
			return null;
		return hotel;
	}



	private String displayRoomsByHotel(String name, String address) throws IOException {


		Hotel hotelName = getHotel(getHotelId(name, address));
		final StringBuilder sb = new StringBuilder(hotelName.getName() + "-" + hotelName.getAddress()+ "-"+hotelName.getId()+" Hotel: \n");
		for (Room room : hotelName.getRooms()) {
			sb.append("Room nr: ").append(room.getNumber());
			sb.append(" Type: ").append(room.getType());
			sb.append("  Status: ").append(room.getStatus()).append("\n");
		}

		return sb.toString();
	}



	public boolean hotelExist(String name, String address) {
		for (Hotel hotel : hotels)
			if (hotel.getName().equalsIgnoreCase(name) && hotel.getAddress().equalsIgnoreCase(address))
				return true;
			return false;

	}


	public int addRoom(int hotelId, RoomType.Type type, int number, double price) throws IOException {

		return getHotel(hotelId).addRoom(type, number, price);
	}


	private Hotel getHotel(int hotelId) throws IOException {

		for (Hotel hotel : hotels)
			if (hotel.getId() == hotelId)
				return hotel;
		throw new IOException("No hotel: " + hotelId);
	}



	public int getHotelId(String name, String address) throws IOException {

		for (Hotel hotel : hotels)
			if (hotel.getName().equalsIgnoreCase(name) && hotel.getAddress().equalsIgnoreCase(address))
				return hotel.getId();
		throw new IOException("No hotel: " + name);

	}


	public int registerCustomer(String name, String address, String password) {
		Guest guest = new Guest(name, address, password);
		guests.add(guest);
		return guest.getId();
	}


	public int book(int customerId, int hotelId, int roomNumber, String bookedDay, String arrivalDate,int nights) throws IOException{

		if (bookingExists(hotelId, roomNumber, arrivalDate) || isBookingDateValid(arrivalDate)) {
			System.out.println("Booking exists for that room on the date.");
			return -1;
		} else {
			Guest guest = getCustomer(customerId);
			Hotel hotel = getHotel(hotelId);
			Room room = hotel.getRoom(roomNumber);
			return addBooking(guest, room, bookedDay, arrivalDate,nights);
		}
	}

	private boolean isBookingDateValid(String date) {
		return DateUtils.isDateAfter(DateUtils.parseDate(date), DateUtils.getToday())
				&& DateUtils.areDatesEquals(DateUtils.parseDate(date), DateUtils.getToday());
	}


	private boolean bookingExists(int hotelId, int roomNumber, String date) throws IOException{
		Hotel hotel = getHotel(hotelId);
		Room room = hotel.getRoom(roomNumber);

		for(Booking b : current) 
			if(b.getRoom() == room && b.hasArrivalDate(date))
				return true;
		return false;
	}


	private int addBooking(Guest guest, Room room, String bookedDay, String arrivalDate, int nights) {

		Booking b = new Booking(guest, room, bookedDay,arrivalDate, nights);
		room.setStatus(Room.RoomStatus.OCCUIPIED);
		current.add(b);
		return b.getId();
	}


	public Guest getCustomer(int customerId) throws IOException{
		for (Guest guest : guests)
			if (guest.getId() == customerId)
				return guest;
		throw new IOException("Cannot find customer: " + customerId);
	}





	public boolean validateLogin(int id, String password) {
		for (Guest guest : guests){
			if(guest.getId()==id && guest.getPassword().equals(password)){
				return true;

			}
		}
		return false;
	}


	public boolean doesGuestExists(String name, String address){

			for (Guest guest : guests) {
				if (guest.getName().equals(name) && guest.getAddress().equals(address))
					return true;
			}

		return false;
	}

// this method sets that the customer starts using the hotel
	public void arrive(int bookingId) throws IOException {
		Booking booking = getBooking(bookingId);
		booking.setArrived(true);
	}


	public Booking getBooking(int bookingId) throws IOException {
		for (Booking booking : current)
			if (booking.getId() == bookingId)
				return booking;
		throw new IOException("No booking with id " + bookingId);
	}


	public void useGym(int bookingId) throws IOException {
		Booking booking = getBooking(bookingId);
		booking.useGym();

	}


	public void buyDrink(int bookingId) throws IOException {
		Booking booking = getBooking(bookingId);
		booking.buyDrink();
	}

    //this methods add meal to account
	public void buyMeal(int bookingId) throws IOException {
		Booking booking = getBooking(bookingId);
		booking.buyMeal();
	}

    //this method sets the checkout date
	public void checkout(int bookingId, String date) throws IOException {
		Booking booking = getBooking(bookingId);
		booking.checkout(date);

	}

    //this method finishes the check out process
	public void payment(int bookingId, double money) throws IOException {
		Booking booking = getBooking(bookingId);
		boolean paid = booking.payment(money);
		if (paid) {
			moveToHistory(booking);
			if(bookingForVIPCustomer(booking)) {
				System.out.println("Setting VIP!");
				booking.getGuest().setVIP(true);
			}
		}
		else
			System.out.println("You offered �" + money
					+ " but the room costs �" + booking.totalCost());
	}

// this method counts how many times a guest booked
	private boolean bookingForVIPCustomer(Booking b) {

		int visits = 0;
		for(Booking history : history)
			if(history.getGuest() == b.getGuest())
				visits++;
		return visits >= 5;
	}

// when the guest checks out and paid, his booking details will be moved to history list
	private void moveToHistory(Booking booking) {
		current.remove(booking);
		history.add(booking);
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BookingSystem");
		sb.append("\n");
		sb.append("guests=").append(guests);
		sb.append("\n");
		sb.append("hotels=").append(hotels);
		sb.append("\n");
		sb.append("current=").append(current);
		sb.append("\n");
		sb.append("booking history=").append(history);
		sb.append("\n");

		return sb.toString();
	}

}
