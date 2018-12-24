package com.hotels;

//class decorator

import java.io.Serializable;

public class Drink extends AdditionalItem implements Serializable {
    private static final double drinkPrice  = 50.00;

    // order a drink at hotel's bar

    public Drink() {
        super(drinkPrice);
    }


}
