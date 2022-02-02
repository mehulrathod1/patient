package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyReviewModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<ReviewData> reviewDataList = new ArrayList<>();

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

    public List<ReviewData> getReviewDataList() {
        return reviewDataList;
    }

    public void setReviewDataList(List<ReviewData> reviewDataList) {
        this.reviewDataList = reviewDataList;
    }

    public static class ReviewData {


        @SerializedName("user_details")
        @Expose
        UserDetail userDetail;

        @SerializedName("message")
        @Expose
        String message;

        @SerializedName("rating")
        @Expose
        String rating;

        @SerializedName("date")
        @Expose
        String date;

        public ReviewData(UserDetail userDetail, String message, String rating, String date) {
            this.userDetail = userDetail;
            this.message = message;
            this.rating = rating;
            this.date = date;
        }

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public class UserDetail {

            @SerializedName("patient_id")
            @Expose
            String patient_id;

            @SerializedName("patient_name")
            @Expose
            String patient_name;

            @SerializedName("patient_image")
            @Expose
            String patient_image;


            public String getPatient_id() {
                return patient_id;
            }

            public void setPatient_id(String patient_id) {
                this.patient_id = patient_id;
            }

            public String getPatient_name() {
                return patient_name;
            }

            public void setPatient_name(String patient_name) {
                this.patient_name = patient_name;
            }

            public String getPatient_image() {
                return patient_image;
            }

            public void setPatient_image(String patient_image) {
                this.patient_image = patient_image;
            }
        }
    }
}
