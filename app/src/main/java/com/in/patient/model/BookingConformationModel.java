package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingConformationModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    ConformationData data;

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

    public ConformationData getData() {
        return data;
    }

    public void setData(ConformationData data) {
        this.data = data;
    }

    public class ConformationData {


        public PatientData getPatientDetails() {
            return PatientDetails;
        }

        public void setPatientDetails(PatientData patientDetails) {
            PatientDetails = patientDetails;
        }

        @SerializedName("Patient Details")
        @Expose
        PatientData PatientDetails;


        @SerializedName("Booking Id")
        @Expose
        String BookingId;

        @SerializedName("Booking For")
        @Expose
        String BookingFor;


        @SerializedName("Specialty")
        @Expose
        String Specialty;
        @SerializedName("Doctor Name")
        @Expose
        String DoctorName;


        @SerializedName("Booking Status")
        @Expose
        String BookingStatus;


        @SerializedName("patient Location")
        @Expose
        String patientLocation;


        @SerializedName("Booked Service Time")
        @Expose
        String BookedServiceTime;


        @SerializedName("Booking Date")
        @Expose
        String BookingDate;

        @SerializedName("Clinic Location")
        @Expose
        String ClinicLocation;

        @SerializedName("Total Amount")
        @Expose
        String TotalAmount;

        @SerializedName("Amount Status")
        @Expose
        String AmountStatus;




        @SerializedName("Doctor Profile")
        @Expose
        String DoctorProfileImage;

        public String getDoctorProfileImage() {
            return DoctorProfileImage;
        }

        public void setDoctorProfileImage(String doctorProfileImage) {
            DoctorProfileImage = doctorProfileImage;
        }

        public String getBookingFor() {
            return BookingFor;
        }

        public void setBookingFor(String bookingFor) {
            BookingFor = bookingFor;
        }

        public String getBookingId() {
            return BookingId;
        }

        public void setBookingId(String bookingId) {
            BookingId = bookingId;
        }

        public String getSpecialty() {
            return Specialty;
        }

        public void setSpecialty(String specialty) {
            Specialty = specialty;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String doctorName) {
            DoctorName = doctorName;
        }

        public String getBookingStatus() {
            return BookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            BookingStatus = bookingStatus;
        }


        public String getPatientLocation() {
            return patientLocation;
        }

        public void setPatientLocation(String patientLocation) {
            this.patientLocation = patientLocation;
        }

        public String getBookedServiceTime() {
            return BookedServiceTime;
        }

        public void setBookedServiceTime(String bookedServiceTime) {
            BookedServiceTime = bookedServiceTime;
        }

        public String getBookingDate() {
            return BookingDate;
        }

        public void setBookingDate(String bookingDate) {
            BookingDate = bookingDate;
        }

        public String getClinicLocation() {
            return ClinicLocation;
        }

        public void setClinicLocation(String clinicLocation) {
            ClinicLocation = clinicLocation;
        }

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            TotalAmount = totalAmount;
        }

        public String getAmountStatus() {
            return AmountStatus;
        }

        public void setAmountStatus(String amountStatus) {


            AmountStatus = amountStatus;
        }


        public class PatientData {


            @SerializedName("Patient Name")
            @Expose
            String PatientName;


            @SerializedName("Patient Age")
            @Expose
            String PatientAge;


            public String getPatientName() {
                return PatientName;
            }

            public void setPatientName(String patientName) {
                PatientName = patientName;
            }

            public String getPatientAge() {
                return PatientAge;
            }

            public void setPatientAge(String patientAge) {
                PatientAge = patientAge;
            }
        }

    }
}
