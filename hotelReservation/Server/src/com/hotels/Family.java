package com.hotels;


// decorator class

public class Family extends RoomType {
    private static final double familyPrice  = 350.00;

    // Room type - for families

    public Family() {
        super(familyPrice);
    }



}
