package com.in.patient.model;

public class CareAndCheckupModel {
    String image, text;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CareAndCheckupModel(String image, String text) {
        this.image = image;
        this.text = text;
    }


}
