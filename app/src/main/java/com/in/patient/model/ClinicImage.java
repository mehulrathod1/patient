package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicImage {

    @SerializedName("clinic_images")
    @Expose
    String clinic_images;

    public String getClinic_images() {
        return clinic_images;
    }

    public void setClinic_images(String clinic_images) {
        this.clinic_images = clinic_images;
    }
}
