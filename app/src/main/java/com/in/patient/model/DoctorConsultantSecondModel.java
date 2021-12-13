package com.in.patient.model;

public class DoctorConsultantSecondModel {

    String DoctorName, speciality, exp, Location, available, LikePercentage, Rate;

    public DoctorConsultantSecondModel(String doctorName, String speciality, String exp, String location, String available, String likePercentage, String rate) {

        DoctorName = doctorName;
        this.speciality = speciality;
        this.exp = exp;
        Location = location;
        this.available = available;
        LikePercentage = likePercentage;
        Rate = rate;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getLikePercentage() {
        return LikePercentage;
    }

    public void setLikePercentage(String likePercentage) {
        LikePercentage = likePercentage;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
