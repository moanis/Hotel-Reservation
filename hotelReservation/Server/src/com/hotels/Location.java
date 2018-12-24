package com.hotels;

import java.io.Serializable;


//abstract class that is for classes using names  and addresses
// it also extends the UniqueId class to produce arbitrary numbers.
public abstract class Location extends UniqueID implements Serializable {





    private String name;

    private String address;

    public Location(){

    }

    public Location(String name, String address) {
        super();
        this.name = name;
        this.address = address;

    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {


        return getClass().getSimpleName()
                + "id=" + getId()
                + " name=" + getName()
                + " address=" + getAddress() + "\n\n";
    }
}
