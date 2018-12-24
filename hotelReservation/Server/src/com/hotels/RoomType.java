package com.hotels;

import java.io.Serializable;

// each room has its own type and its own price


public abstract class RoomType implements Serializable {


    public enum Type {
        Single, Double, Family, Suite
    }

    private double price;

    public RoomType(double price) {
        super();
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {

        return getClass().getSimpleName() + " Cost=" + getPrice();
    }



}
