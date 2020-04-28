package com.github.brianmmcclain.springbootapidemo;

public class Item {

    private final long id;
    private String name;
    private int count;

    public Item(long id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public long getId() {
        return this.id;
    }

    public String getName() { 
        return this.name;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}