package com.github.brianmmcclain.springbootapidemo;

/**
 * Defines the Item object, which defines an ID, name, and inventory count
 */
public class Item {

    private final long id;
    private String name;
    private double price;
    private int count;

    public Item(long id, String name, double price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public long getId() {
        return this.id;
    }

    public String getName() { 
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}