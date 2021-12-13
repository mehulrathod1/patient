package com.in.patient.model;

public class ChatModel {

    String itemImage, name, message;

    public ChatModel(String itemImage, String name, String message) {
        this.itemImage = itemImage;
        this.name = name;
        this.message = message;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
