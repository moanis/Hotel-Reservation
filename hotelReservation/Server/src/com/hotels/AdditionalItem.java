package com.hotels;


// An additional item is something that the customer
// can buy or use at the hotel

public abstract class AdditionalItem {
    


    private double cost;

    public AdditionalItem(double cost) {
	super();
	this.cost = cost;
    }

    public double getCost() {
	return cost;
    }

    public void setCost(double cost) {
	this.cost = cost;
    }

//    public abstract void xml(XMLOutput out);

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName()+"{");
        sb.append(" cost= ").append(getCost());
        sb.append('}');
        return sb.toString();
    }

}
