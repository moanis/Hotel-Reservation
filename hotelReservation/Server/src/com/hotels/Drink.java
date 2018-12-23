package com.hotels;

//class decorator

public class Drink extends AdditionalItem {
    public static final double drinkPrice  = 50.00;
    
    // order a drink at hotel's bar

    public Drink() {
	super(drinkPrice);
    }


}
