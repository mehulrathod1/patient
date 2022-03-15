package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LabModel {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<LabListData > labListData = new ArrayList<>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LabListData> getLabListData() {
        return labListData;
    }

    public void setLabListData(List<LabListData> labListData) {
        this.labListData = labListData;
    }

    public static class LabListData {


        @SerializedName("lab_id")
        @Expose
        String lab_id;


        @SerializedName("labname")
        @Expose
        String lab_name;

        @SerializedName("location")
        @Expose
        String location;


        @SerializedName("image")
        @Expose
        String image;

        public LabListData(String lab_id, String lab_name, String location, String image) {
            this.lab_id = lab_id;
            this.lab_name = lab_name;
            this.location = location;
            this.image = image;
        }

        public String getLab_id() {
            return lab_id;
        }

        public void setLab_id(String lab_id) {
            this.lab_id = lab_id;
        }

        public String getLab_name() {
            return lab_name;
        }

        public void setLab_name(String lab_name) {
            this.lab_name = lab_name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
