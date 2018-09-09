package com.sokmo.sokmoapp;



public class Item {

    public String text;
    public int drawable;
    public String title;

    public Item(String text, int drawable, String title ) {
        this.text = text;
        this.drawable = drawable;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getColor() {
        return title;
    }

    public void setColor(String color) {
        this.title = title;
    }
}
