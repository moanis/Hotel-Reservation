package com.hotels;

//a class decorator
public class Suite extends RoomType {
    public static final double suitePrice  = 450.00;


    // a room type - suite
    public Suite() {
	super(suitePrice);
    }


}
