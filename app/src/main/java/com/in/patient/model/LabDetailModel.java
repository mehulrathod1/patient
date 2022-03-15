package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabDetailModel {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    LabDetailData data;

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

    public LabDetailData getData() {
        return data;
    }

    public void setData(LabDetailData data) {
        this.data = data;
    }

    public class LabDetailData {

        @SerializedName("lab_id")
        @Expose
        String lab_id;


        @SerializedName("lab_name")
        @Expose
        String lab_name;


        @SerializedName("lab_image")
        @Expose
        String lab_image;


        @SerializedName("email")
        @Expose
        String email;


        @SerializedName("mobile_no")
        @Expose
        String mobile_no;


        @SerializedName("city")
        @Expose
        String city;


        @SerializedName("address")
        @Expose
        String address;



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

        public String getLab_image() {
            return lab_image;
        }

        public void setLab_image(String lab_image) {
            this.lab_image = lab_image;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
