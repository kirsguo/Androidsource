package com.example.kirsguo.listviewtest;

/**
 * Created by Kirsguo on 2017/4/6.
 */

public class Fruit {
//    名称
    private String name;
//    图片id
    private int imageID;

    public Fruit(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
