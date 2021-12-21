package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfileData {


    @SerializedName("Doctor Details")
    @Expose
    public DoctorDetails doctorDetails;

    @SerializedName("Clinic Details")
    @Expose
    public ClinicDetails clinicDetails;

    public DoctorDetails getDoctorDetails() {
        return doctorDetails;
    }

    public void setDoctorDetails(DoctorDetails doctorDetails) {
        this.doctorDetails = doctorDetails;
    }

    public ClinicDetails getClinicDetails() {
        return clinicDetails;
    }

    public void setClinicDetails(ClinicDetails clinicDetails) {
        this.clinicDetails = clinicDetails;
    }

    public class DoctorDetails {
        @SerializedName("doctor_name")
        @Expose
        private String doctor_name;

        @SerializedName("education")
        @Expose
        private String education;

        @SerializedName("specialist")
        @Expose
        private String specialist;

        @SerializedName("language_spoken")
        @Expose
        private String language_spoken;

        @SerializedName("experience")
        @Expose
        private String experience;

        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getSpecialist() {
            return specialist;
        }

        public void setSpecialist(String specialist) {
            this.specialist = specialist;
        }

        public String getLanguage_spoken() {
            return language_spoken;
        }

        public void setLanguage_spoken(String language_spoken) {
            this.language_spoken = language_spoken;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }
    }

    public class ClinicDetails {


        @SerializedName("ofline_consultancy_fees")
        @Expose
        private String oflineConsultancyFees;

        @SerializedName("from_to_days")
        @Expose
        private String fromToDays;

        @SerializedName("open_close_time")
        @Expose
        private String openCloseTime;

        @SerializedName("clinic_name")
        @Expose
        private String clinicName;

        @SerializedName("location")
        @Expose
        private String location;

        @SerializedName("doctor_availability_status")
        @Expose
        private String doctorAvailabilityStatus;

        public String getOflineConsultancyFees() {
            return oflineConsultancyFees;
        }

        public void setOflineConsultancyFees(String oflineConsultancyFees) {
            this.oflineConsultancyFees = oflineConsultancyFees;
        }

        public String getFromToDays() {
            return fromToDays;
        }

        public void setFromToDays(String fromToDays) {
            this.fromToDays = fromToDays;
        }

        public String getOpenCloseTime() {
            return openCloseTime;
        }

        public void setOpenCloseTime(String openCloseTime) {
            this.openCloseTime = openCloseTime;
        }

        public String getClinicName() {
            return clinicName;
        }

        public void setClinicName(String clinicName) {
            this.clinicName = clinicName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDoctorAvailabilityStatus() {
            return doctorAvailabilityStatus;
        }

        public void setDoctorAvailabilityStatus(String doctorAvailabilityStatus) {
            this.doctorAvailabilityStatus = doctorAvailabilityStatus;
        }
    }
}
