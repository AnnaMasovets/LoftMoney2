package com.ashagunova.loftmoney_2.cell;

import com.ashagunova.loftmoney_2.remote.MoneyRemoteItem;

public class Item {

    private String name;
    private String price;
    private boolean isSelected;


    public Item(String name, String price) {
        this.name = name;
        this.price = price;
        this.isSelected = false;
        //this.currentPosition = currentPosition;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static Item getInstance(MoneyRemoteItem moneyRemoteItem) {
        return new Item(moneyRemoteItem.getName(), moneyRemoteItem.getPrice() + "$");
    }


    //public int getCurrentPosition() { return currentPosition; }
}
