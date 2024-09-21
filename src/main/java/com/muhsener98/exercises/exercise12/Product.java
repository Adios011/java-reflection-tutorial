package com.muhsener98.exercises.exercise12;

public class Product {

    private double price;
    private String name ;
    private long quantity;
    private String prop ;

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getProp() {
        return prop;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setProp(String prop) {
         this.prop = prop;
    }
}
