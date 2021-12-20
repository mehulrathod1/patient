package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.patient.fragment.Medicines;

import java.util.ArrayList;
import java.util.List;

public class MedicinesModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<MedicinesData> medicinesDataList = new ArrayList<>();

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

    public List<MedicinesData> getMedicinesDataList() {
        return medicinesDataList;
    }

    public void setMedicinesDataList(List<MedicinesData> medicinesDataList) {
        this.medicinesDataList = medicinesDataList;
    }

    public static class MedicinesData {

        @SerializedName("medicines_name")
        @Expose
        String medicines_name;

        @SerializedName("price")
        @Expose
        String price;
        @SerializedName("description")
        @Expose
        String description;
        @SerializedName("medicine_image")
        @Expose
        String medicine_image;


        public MedicinesData(String medicines_name, String price, String description, String medicine_image) {
            this.medicines_name = medicines_name;
            this.price = price;
            this.description = description;
            this.medicine_image = medicine_image;
        }

        public String getMedicines_name() {
            return medicines_name;
        }

        public void setMedicines_name(String medicines_name) {
            this.medicines_name = medicines_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMedicine_image() {
            return medicine_image;
        }

        public void setMedicine_image(String medicine_image) {
            this.medicine_image = medicine_image;
        }
    }
}
