package com.hotels;

//class decorator
public class Gym extends AdditionalItem {
    public static final double gymPrice  = 100.00;
    
    // when a guest use a hotel gym facility
    public Gym() {
	super(gymPrice);
    }


}
