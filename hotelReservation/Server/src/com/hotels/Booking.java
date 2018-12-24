package com.hotels;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;



//Booking class is a contract between the guest and a room. First he guest
//books a room(a booking instance is created). The guest then should arrives and
//check in at the hotel. The guest then will have access to the room and hotel facilities

public class Booking extends UniqueID implements Serializable {

	private LocalDate dateBooked = null; // Set the date when the booking is created.

	private LocalDate  dateArrived = null; // Set the date when the guest checks in.

	private LocalDate dateCheckedOut = null; // Set the date when the guest checks out.

	private boolean arrived = false; // True when the guest has arrived.

	private boolean paid = false; // True when the guest has paid.

	private boolean isCheckedOut = false; //True when the guest has checked out.

	private Guest guest;

	private Room room;
	
	private int numberOfNights;

	private ArrayList<AdditionalItem> costs = new ArrayList<>();




	public Booking(Guest guest, Room room, String dateBooked, String dateArrived, int numberOfNights) {

		super();
		this.guest = guest;
		this.room = room;
		this.numberOfNights = numberOfNights;
		this.dateBooked = DateUtils.parseDate(dateBooked);
		this.dateArrived = DateUtils.parseDate(dateArrived);

	}

	public ArrayList<AdditionalItem> getCosts() {
		return costs;
	}

	public Guest getGuest() {
		return guest;
	}

	public Room getRoom() {

		return room;

	}

	public boolean isArrived() {
		return arrived;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public boolean isCheckedOut() {
		return isCheckedOut;
	}

	public void setDateCheckedOut(LocalDate dateCheckedOut) {
		this.dateCheckedOut = dateCheckedOut;
		isCheckedOut = true;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}




	//calculate the days between to dates

	public int periodIndays() {

		if(dateCheckedOut==null && (DateUtils.isDateBefore(DateUtils.getToday(), dateArrived))) {
			return numberOfNights;
		}else if (dateCheckedOut==null && (DateUtils.isDateAfter(DateUtils.getToday(), dateArrived)))
			return DateUtils.calculateDays(dateArrived, DateUtils.getToday());;
		if (DateUtils.isDateAfter(dateCheckedOut, dateArrived) && !DateUtils.areDatesEquals(dateCheckedOut, dateArrived)
				&& DateUtils.isDateAfter(dateCheckedOut, DateUtils.getToday())) {
			return DateUtils.calculateDays(dateArrived, dateCheckedOut);
		}

		return 0;
	}

	// The total cost of the stay
	public double totalCost() {

		double price = periodIndays() * room.getType().getPrice() + additionalCosts();
		if(guest.isVIP())
			return price - (0.1*price);
		else return price;
	}


	// Calculate the cost of all the facilities used in the hotel

	private double additionalCosts() {

		double price = 0.0;
		for (AdditionalItem item : costs)
			price = price + item.getCost();
		return price;
	}


	public void checkout(String date) {
		setDateCheckedOut(DateUtils.parseDate(date));
	}

	public boolean payment(double money) {

		if(money >= totalCost())
			setPaid(true);
		else
			setPaid(false);
		return isPaid();
	}



	public void buyDrink() {
		costs.add(new Drink());
	}

	public void buyMeal() {
		costs.add(new Meal());
	}


	public void useGym() {
		costs.add(new Gym());
	}

	public LocalDate getDateArrived() {
		return dateArrived;
	}



	public boolean hasArrivalDate(String date) {

		LocalDate d = DateUtils.parseDate(date);
		System.out.println("Comparing " + d + " with " + dateArrived + " = " + dateArrived.toString().equals(d.toString()));
		return dateArrived.toString().equals(d.toString());

	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("dateBooked=").append(dateBooked).append("\n");
		sb.append(", dateArrived=").append(dateArrived).append("\n");
		sb.append(", dateCheckedOut=").append(dateCheckedOut).append("\n");
		sb.append(", arrived=").append(arrived).append("\n");
		sb.append(", paid=").append(paid).append("\n");
		sb.append(", guest=").append(guest).append("\n");
		sb.append(", room=").append(getRoom()).append("\n");
		sb.append(", numberOfNights=").append(numberOfNights).append("\n");
		sb.append(", costs=").append(costs);
		sb.append(", totalCost=").append(totalCost());
		sb.append("\n");
		sb.append('}');
		sb.append("\n");

		return sb.toString();
	}


}
