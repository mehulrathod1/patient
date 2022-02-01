package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewBookingDetailModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    BookingData bookingData;

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

    public BookingData getBookingData() {
        return bookingData;
    }

    public void setBookingData(BookingData bookingData) {
        this.bookingData = bookingData;
    }

    public class BookingData {


        @SerializedName("Doctor Id")
        @Expose
        String DoctorId;

        @SerializedName("Booking Id")
        @Expose
        String BookingId;


        @SerializedName("Specialty")
        @Expose
        String Specialty;

        @SerializedName("Doctor Name")
        @Expose
        String DoctorName;

        @SerializedName("Booking Status")
        @Expose
        String BookingStatus;


        @SerializedName("Patient Name")
        @Expose
        String PatientName;


        @SerializedName("patient Location")
        @Expose
        String patientLocation;


        @SerializedName("Booked Date")
        @Expose
        String BookedDate;

        @SerializedName("Booked Service Time")
        @Expose
        String BookedServiceTime;

        @SerializedName("Clinic Location")
        @Expose
        String ClinicLocation;

        @SerializedName("Total Amount")
        @Expose
        String TotalAmount;

        @SerializedName("Amount Status")
        @Expose
        String AmountStatus;


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

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String patientName) {
            PatientName = patientName;
        }

        public String getPatientLocation() {
            return patientLocation;
        }

        public void setPatientLocation(String patientLocation) {
            this.patientLocation = patientLocation;
        }

        public String getBookedDate() {
            return BookedDate;
        }

        public void setBookedDate(String bookedDate) {
            BookedDate = bookedDate;
        }

        public String getBookedServiceTime() {
            return BookedServiceTime;
        }

        public void setBookedServiceTime(String bookedServiceTime) {
            BookedServiceTime = bookedServiceTime;
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

        public String getDoctorId() {
            return DoctorId;
        }

        public void setDoctorId(String doctorId) {
            DoctorId = doctorId;
        }
    }



}
