package com.in.patient.model;

public class FindDoctorModel {

    String profileImage,category;

    public FindDoctorModel(String profileImage, String category) {
        this.profileImage = profileImage;
        this.category = category;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
