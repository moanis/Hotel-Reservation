package com.hotels;

import java.io.Serializable;

//class decorator
public class Meal extends AdditionalItem implements Serializable {
    public static final double mealPrice = 120.00;

    // when you buy a meal at the hotel restaurant

    public Meal() {
        super(mealPrice);
    }


}
