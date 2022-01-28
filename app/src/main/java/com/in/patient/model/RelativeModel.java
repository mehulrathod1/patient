package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RelativeModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<RelativeData> relativeDataList = new ArrayList<>();

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

    public List<RelativeData> getRelativeDataList() {
        return relativeDataList;
    }

    public void setRelativeDataList(List<RelativeData> relativeDataList) {
        this.relativeDataList = relativeDataList;
    }

    public static class RelativeData {


        @SerializedName("relative_name")
        @Expose
        String relative_name;


        @SerializedName("relation")
        @Expose
        String relation;


        @SerializedName("age")
        @Expose
        String age;


        @SerializedName("blood_group")
        @Expose
        String blood_group;


        @SerializedName("marital_status")
        @Expose
        String marital_status;


        @SerializedName("gender")
        @Expose
        String gender;

        public RelativeData(String relative_name, String relation, String age, String blood_group, String marital_status, String gender) {
            this.relative_name = relative_name;
            this.relation = relation;
            this.age = age;
            this.blood_group = blood_group;
            this.marital_status = marital_status;
            this.gender = gender;
        }

        public String getRelative_name() {
            return relative_name;
        }

        public void setRelative_name(String relative_name) {
            this.relative_name = relative_name;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
