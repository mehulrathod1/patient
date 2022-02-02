package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendNotificationModel {

    @SerializedName("data")
    @Expose
    public SendNotification data;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private boolean status;

    public SendNotification getData() {
        return data;
    }

    public void setData(SendNotification data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class SendNotification {


        @SerializedName("user_id")
        @Expose
        private String user_id;

        @SerializedName("Channel Name")
        @Expose
        private String ChannelName;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getChannelName() {
            return ChannelName;
        }

        public void setChannelName(String channelName) {
            ChannelName = channelName;
        }
    }
}