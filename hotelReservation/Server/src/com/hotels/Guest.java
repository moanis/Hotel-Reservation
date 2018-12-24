package com.hotels;


//when someone signs up, a unique id number is allocated by the system (which is basically
//like a counter). The guest id then can be used by the booking system

public class Guest extends Location {

	private String password;
	private boolean isVIP = false;
	private boolean isSignedUp = false;
	private boolean isLoggedIn = false;


	public Guest(String name, String address, String password) {
		super(name, address);
		this.password = password;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		isLoggedIn = loggedIn;
	}

	public boolean isSignedUp() {
		return isSignedUp;
	}

	public void setSignedUp(boolean signedUp) {
		isSignedUp = signedUp;
	}

	public boolean isVIP() {
		return isVIP;
	}

	public void setVIP(boolean isGold) {
		this.isVIP = isGold;
	}

	public Guest(String name, String address) {

		super(name, address);
	}


	public String getPassword() {
		return password;
	}





	@Override
	public String toString() {

		return "{"
				+ "Id=" + getId() + "\t"
				+ "Name=" + getName() + "\t"
				+"Address" + getPassword() + "\t";
	}
}
