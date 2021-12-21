package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfileModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    DoctorProfileData data;

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

    public DoctorProfileData getData() {
        return data;
    }

    public void setData(DoctorProfileData data) {
        this.data = data;
    }
}
