package com.in.patient.model;

public class LabModel {

    String LabName, description, Location;

    public LabModel(String labName, String description, String location) {
        LabName = labName;
        this.description = description;
        Location = location;
    }

    public String getLabName() {
        return LabName;
    }

    public void setLabName(String labName) {
        LabName = labName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
