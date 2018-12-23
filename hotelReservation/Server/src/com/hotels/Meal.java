package com.hotels;

//class decorator
public class Meal extends AdditionalItem {
    public static final double mealPrice  = 120.00;
    
    // when you buy a meal at the hotel restaurant

    public Meal() {
	super(mealPrice);
    }


}
