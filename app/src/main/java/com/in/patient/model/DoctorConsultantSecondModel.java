package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DoctorConsultantSecondModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<ConsultantData> consultantDataList = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ConsultantData> getConsultantDataList() {
        return consultantDataList;
    }

    public void setConsultantDataList(List<ConsultantData> consultantDataList) {
        this.consultantDataList = consultantDataList;
    }

    public static class ConsultantData {

        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("first_name")
        @Expose
        String first_name;

        @SerializedName("last_name")
        @Expose
        String last_name;

        @SerializedName("specialist")
        @Expose
        String specialist;

        @SerializedName("experience")
        @Expose
        String experience;

        @SerializedName("location")
        @Expose
        String location;

        @SerializedName("available")
        @Expose
        String available;

        @SerializedName("profile_image")
        @Expose
        String profile_image;


        public ConsultantData(String user_id, String first_name, String last_name, String specialist, String experience, String location, String available, String profile_image) {
            this.user_id = user_id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.specialist = specialist;
            this.experience = experience;
            this.location = location;
            this.available = available;
            this.profile_image = profile_image;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getSpecialist() {
            return specialist;
        }

        public void setSpecialist(String specialist) {
            this.specialist = specialist;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }
    }
}
