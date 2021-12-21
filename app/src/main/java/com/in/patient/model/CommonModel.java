package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonModel {
    @SerializedName("status")
    @Expose
    Boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    String data;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
