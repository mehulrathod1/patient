package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChatDashboardModel {


    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<DashboardMessage> dashboardMessageList = new ArrayList<>();

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

    public List<DashboardMessage> getDashboardMessageList() {
        return dashboardMessageList;
    }

    public void setDashboardMessageList(List<DashboardMessage> dashboardMessageList) {
        this.dashboardMessageList = dashboardMessageList;
    }

    public static class DashboardMessage {

        @SerializedName("doctor_id")
        @Expose
        String doctor_id;

        @SerializedName("patient_id")
        @Expose
        String patient_id;

        @SerializedName("send_by")
        @Expose
        String send_by;

        @SerializedName("message")
        @Expose
        String message;

        @SerializedName("date")
        @Expose
        String date;

        @SerializedName("time")
        @Expose
        String time;

        @SerializedName("chat_image")
        @Expose
        String chat_image;


        public DashboardMessage(String doctor_id, String patient_id, String send_by, String message, String date, String time, String chat_image) {
            this.doctor_id = doctor_id;
            this.patient_id = patient_id;
            this.send_by = send_by;
            this.message = message;
            this.date = date;
            this.time = time;
            this.chat_image = chat_image;
        }

        public String getChat_image() {
            return chat_image;
        }

        public void setChat_image(String chat_image) {
            this.chat_image = chat_image;
        }

        public String getDoctor_id() {
            return doctor_id;
        }

        public void setDoctor_id(String doctor_id) {
            this.doctor_id = doctor_id;
        }

        public String getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(String patient_id) {
            this.patient_id = patient_id;
        }

        public String getSend_by() {
            return send_by;
        }

        public void setSend_by(String send_by) {
            this.send_by = send_by;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
