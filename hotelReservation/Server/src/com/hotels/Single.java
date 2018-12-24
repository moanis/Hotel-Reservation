package com.hotels;

//a class decorator

public class Single extends RoomType {
    private static final double singlePrice  = 200.00;

    // a room type - single

    public Single() {

        super(singlePrice);
    }



}
