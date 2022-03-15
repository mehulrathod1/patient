package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RazorpayKeyModel {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    RazorpayKey data;

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

    public RazorpayKey getData() {
        return data;
    }

    public void setData(RazorpayKey data) {
        this.data = data;
    }

    public class RazorpayKey {

        @SerializedName("razorpay_key_id")
        @Expose
        String razorpay_key_id;

        @SerializedName("razorpay_key_secret")
        @Expose
        String razorpay_key_secret;

        public String getRazorpay_key_id() {
            return razorpay_key_id;
        }

        public void setRazorpay_key_id(String razorpay_key_id) {
            this.razorpay_key_id = razorpay_key_id;
        }

        public String getRazorpay_key_secret() {
            return razorpay_key_secret;
        }

        public void setRazorpay_key_secret(String razorpay_key_secret) {
            this.razorpay_key_secret = razorpay_key_secret;
        }
    }
}
