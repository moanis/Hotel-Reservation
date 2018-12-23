package com.hotels;

import java.util.Random;



//A unique number is only one number across all the system.


public abstract class UniqueID {
    


    private static int counter = 0;
    private Random rand = new Random();


    private int id = rand.nextInt(10000);;


    public synchronized int getId() {

        return id;
    }

}
