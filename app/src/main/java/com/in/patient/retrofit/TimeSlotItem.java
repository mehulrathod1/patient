package com.in.patient.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotItem {

    @SerializedName("slot_time")
    @Expose
    private String slotTime;

    @SerializedName("status")
    @Expose
    private String status;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    private Boolean isSelected = false;

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public TimeSlotItem(String slotTime, String status) {
        this.slotTime = slotTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "TimeSlotItem{" +
                        "slot_time = '" + slotTime + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}