package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeSlotModel {


    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("message")
    @Expose
    TimeData timeData;

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

    public TimeData getTimeData() {
        return timeData;
    }

    public void setTimeData(TimeData timeData) {
        this.timeData = timeData;
    }

    public static class TimeData {

        @SerializedName("Time Slot")
        @Expose
        List<TimeSlot> timeSlotList = new ArrayList<>();

        public List<TimeSlot> getTimeSlotList() {
            return timeSlotList;
        }

        public void setTimeSlotList(List<TimeSlot> timeSlotList) {
            this.timeSlotList = timeSlotList;
        }


    }
}
