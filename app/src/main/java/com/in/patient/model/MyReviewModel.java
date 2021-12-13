package com.in.patient.model;

public class MyReviewModel {
    String profileImage,Name,Date,ReviewText;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getReviewText() {
        return ReviewText;
    }

    public void setReviewText(String reviewText) {
        ReviewText = reviewText;
    }

    public MyReviewModel(String profileImage, String name, String date, String reviewText) {
        this.profileImage = profileImage;
        Name = name;
        Date = date;
        ReviewText = reviewText;
    }
}
