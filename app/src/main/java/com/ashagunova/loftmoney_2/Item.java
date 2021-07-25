package com.ashagunova.loftmoney_2;

public class Item {

    private String name;
    private String price;
    private int currentPosition;

    public Item(String name, String price, int currentPosition) {
        this.name = name;
        this.price = price;
        this.currentPosition = currentPosition;
    }

    //public void setName(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getCurrentPosition() { return currentPosition; }
}
