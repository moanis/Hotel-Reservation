package com.hotels;

import java.io.Serializable;

//class decorator
public class Gym extends AdditionalItem implements Serializable {
    private static final double gymPrice  = 100.00;

    // when a guest use a hotel gym facility
    public Gym() {
        super(gymPrice);
    }


}
