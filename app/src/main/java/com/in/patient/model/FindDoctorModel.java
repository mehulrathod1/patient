package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FindDoctorModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<DoctorSpecialities> doctorSpecialitiesList = new ArrayList<>();

    public List<DoctorSpecialities> getDoctorSpecialitiesList() {
        return doctorSpecialitiesList;
    }

    public void setDoctorSpecialitiesList(List<DoctorSpecialities> doctorSpecialitiesList) {
        this.doctorSpecialitiesList = doctorSpecialitiesList;
    }

    public static class DoctorSpecialities {

        @SerializedName("Specialist id")
        @Expose
        String SpecialistId;

        @SerializedName("Specialist Name")
        @Expose
        String SpecialistName;

        @SerializedName("Specialist img")
        @Expose
        String SpecialistImg;

        public DoctorSpecialities(String specialistId, String specialistName, String specialistImg) {
            SpecialistId = specialistId;
            SpecialistName = specialistName;
            SpecialistImg = specialistImg;
        }

        public String getSpecialistId() {
            return SpecialistId;
        }

        public void setSpecialistId(String specialistId) {
            SpecialistId = specialistId;
        }

        public String getSpecialistName() {
            return SpecialistName;
        }

        public void setSpecialistName(String specialistName) {
            SpecialistName = specialistName;
        }

        public String getSpecialistImg() {
            return SpecialistImg;
        }

        public void setSpecialistImg(String specialistImg) {
            SpecialistImg = specialistImg;
        }
    }
}
