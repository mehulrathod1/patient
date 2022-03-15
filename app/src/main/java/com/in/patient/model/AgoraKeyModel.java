package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgoraKeyModel {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    AgoraKey data;

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

    public AgoraKey getData() {
        return data;
    }

    public void setData(AgoraKey data) {
        this.data = data;
    }

    public class AgoraKey {

        @SerializedName("agora_appid")
        @Expose
        String agora_app_id;


        public String getAgora_app_id() {
            return agora_app_id;
        }

        public void setAgora_app_id(String agora_app_id) {
            this.agora_app_id = agora_app_id;
        }
    }
}
