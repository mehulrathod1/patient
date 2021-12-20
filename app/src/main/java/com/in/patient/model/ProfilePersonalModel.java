package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePersonalModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    ProfilePersonal data;


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

    public ProfilePersonal getData() {
        return data;
    }

    public void setData(ProfilePersonal data) {
        this.data = data;
    }

    public class ProfilePersonal {
        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("first_name")
        @Expose
        String first_name;

        @SerializedName("last_name")
        @Expose
        String last_name;

        @SerializedName("email")
        @Expose
        String email;

        @SerializedName("mobile_number")
        @Expose
        String mobile_number;

        @SerializedName("age")
        @Expose
        String age;

        @SerializedName("gender")
        @Expose
        String gender;

        @SerializedName("DOB")
        @Expose
        String DOB;

        @SerializedName("blood_group")
        @Expose
        String blood_group;

        @SerializedName("marital_status")
        @Expose
        String marital_status;

        @SerializedName("height")
        @Expose
        String height;

        @SerializedName("weight")
        @Expose
        String weight;

        @SerializedName("emergency_contact")
        @Expose
        String emergency_contact;

        @SerializedName("Address")
        @Expose
        String Address;

        @SerializedName("Profile")
        @Expose
        String Profile;


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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getBlood_group() {
            return blood_group;
        }

        public void setBlood_group(String blood_group) {
            this.blood_group = blood_group;
        }

        public String getMarital_status() {
            return marital_status;
        }

        public void setMarital_status(String marital_status) {
            this.marital_status = marital_status;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getEmergency_contact() {
            return emergency_contact;
        }

        public void setEmergency_contact(String emergency_contact) {
            this.emergency_contact = emergency_contact;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getProfile() {
            return Profile;
        }

        public void setProfile(String profile) {
            Profile = profile;
        }
    }


}
