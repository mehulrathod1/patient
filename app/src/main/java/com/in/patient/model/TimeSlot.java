package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlot {

    @SerializedName("slot_time")
    @Expose
    private String slotTime;

    @SerializedName("status")
    @Expose
    private String status;

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
