package com.hotels;


//class decorator
public class Double extends RoomType {
    public static final double doublePrice  = 250.00;
    
    // room type - double

    public Double() {
	super(doublePrice);
    }


}
