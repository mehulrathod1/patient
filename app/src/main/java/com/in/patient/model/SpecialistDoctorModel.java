package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpecialistDoctorModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<Specialist> specialistList = new ArrayList<>();

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

    public List<Specialist> getSpecialistList() {
        return specialistList;
    }

    public void setSpecialistList(List<Specialist> specialistList) {
        this.specialistList = specialistList;
    }

    public static class Specialist {

        @SerializedName("Doctor Id")
        @Expose
        String DoctorId;

        @SerializedName("Doctor Name")
        @Expose
        String DoctorName;

        @SerializedName("Experience")
        @Expose
        String Experience;

        @SerializedName("Specialist")
        @Expose
        String Specialist;


        @SerializedName("Location")
        @Expose
        String Location;

        @SerializedName("available")
        @Expose
        String available;

        @SerializedName("Profile")
        @Expose
        String Profile;

        public Specialist(String doctorId, String doctorName, String experience, String specialist, String location, String available, String profile) {
            DoctorId = doctorId;
            DoctorName = doctorName;
            Experience = experience;
            Specialist = specialist;
            Location = location;
            this.available = available;
            Profile = profile;
        }

        public String getDoctorId() {
            return DoctorId;
        }

        public void setDoctorId(String doctorId) {
            DoctorId = doctorId;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String doctorName) {
            DoctorName = doctorName;
        }

        public String getExperience() {
            return Experience;
        }

        public void setExperience(String experience) {
            Experience = experience;
        }

        public String getSpecialist() {
            return Specialist;
        }

        public void setSpecialist(String specialist) {
            Specialist = specialist;
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

        public String getProfile() {
            return Profile;
        }

        public void setProfile(String profile) {
            Profile = profile;
        }
    }
}
