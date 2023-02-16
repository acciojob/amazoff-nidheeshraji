package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        int hours=0;
        int minutes=0;

        hours= Integer.parseInt(deliveryTime.substring(0,2));
        minutes= Integer.parseInt(deliveryTime.substring(3,5));
        hours=hours*60;
        this.deliveryTime=hours+minutes;

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
