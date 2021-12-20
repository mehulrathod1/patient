package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<AppointmentData> medicinesDataList = new ArrayList<>();

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

    public List<AppointmentData> getMedicinesDataList() {
        return medicinesDataList;
    }

    public void setMedicinesDataList(List<AppointmentData> medicinesDataList) {
        this.medicinesDataList = medicinesDataList;
    }

    public static class AppointmentData {
        @SerializedName("booing id")
        @Expose
        String booingId;

        @SerializedName("Doctor Name")
        @Expose
        String DoctorName;

        @SerializedName("Location")
        @Expose
        String Location;

        @SerializedName("Status")
        @Expose
        String Status;

        @SerializedName("Profile")
        @Expose
        String Profile;

        @SerializedName("Booking Date")
        @Expose
        String BookingDate;

        @SerializedName("Fees")
        @Expose
        String Fees;


        public AppointmentData(String booingId, String doctorName, String location, String status, String profile, String bookingDate, String fees) {
            this.booingId = booingId;
            DoctorName = doctorName;
            Location = location;
            Status = status;
            Profile = profile;
            BookingDate = bookingDate;
            Fees = fees;
        }

        public String getBooingId() {
            return booingId;
        }

        public void setBooingId(String booingId) {
            this.booingId = booingId;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String doctorName) {
            DoctorName = doctorName;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getProfile() {
            return Profile;
        }

        public void setProfile(String profile) {
            Profile = profile;
        }

        public String getBookingDate() {
            return BookingDate;
        }

        public void setBookingDate(String bookingDate) {
            BookingDate = bookingDate;
        }

        public String getFees() {
            return Fees;
        }

        public void setFees(String fees) {
            Fees = fees;
        }
    }
}
