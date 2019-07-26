package com.example.bouchef.tubolsillo.model;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class Review {

    String title;
    Integer image;

    public Review(String title, Integer image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
