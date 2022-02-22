package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<ChatDoctorList> chatDoctorLists = new ArrayList<>();

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

    public List<ChatDoctorList> getChatDoctorLists() {
        return chatDoctorLists;
    }

    public void setChatDoctorLists(List<ChatDoctorList> chatDoctorLists) {
        this.chatDoctorLists = chatDoctorLists;
    }

    public static class ChatDoctorList {

        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("user_name")
        @Expose
        String user_name;

        @SerializedName("profile_image")
        @Expose
        String profile_image;

        @SerializedName("message")
        @Expose
        String message;

        public ChatDoctorList(String user_id, String user_name, String profile_image, String message) {
            this.user_id = user_id;
            this.user_name = user_name;
            this.profile_image = profile_image;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }
    }
}
