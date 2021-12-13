package com.in.patient.model;

public class MedicinesModel {
    String MedicineName, MedicineDescription, MedicinePrice;

    public MedicinesModel(String medicineName, String medicineDescription, String medicinePrice) {
        MedicineName = medicineName;
        MedicineDescription = medicineDescription;
        MedicinePrice = medicinePrice;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public String getMedicineDescription() {
        return MedicineDescription;
    }

    public void setMedicineDescription(String medicineDescription) {
        MedicineDescription = medicineDescription;
    }

    public String getMedicinePrice() {
        return MedicinePrice;
    }

    public void setMedicinePrice(String medicinePrice) {
        MedicinePrice = medicinePrice;
    }
}
