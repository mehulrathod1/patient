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


        @SerializedName("Booking Date")
        @Expose
        String BookedDate;

        @SerializedName("Booking Time")
        @Expose
        String BookingTime;

        @SerializedName("Appointment Time")
        @Expose
        String AppointmentTime;

        @SerializedName("Appointment Date")
        @Expose
        String AppointmentDate;


        @SerializedName("Booked Service Time")
        @Expose
        String BookedServiceTime;

        @SerializedName("Clinic Location")
        @Expose
        String ClinicLocation;

        @SerializedName("to_be_paid")
        @Expose
        String TotalAmount;

        @SerializedName("Amount Status")
        @Expose
        String AmountStatus;

        @SerializedName("doctor report")
        @Expose
        String doctor_report;


        @SerializedName("video_consultancy_complete")
        @Expose
        String video_consultancy_complete;

        public BookingData(String doctorId, String bookingId, String specialty, String doctorName, String bookingStatus, String patientName, String patientLocation, String bookedDate, String bookingTime, String appointmentTime, String appointmentDate, String bookedServiceTime, String clinicLocation, String totalAmount, String amountStatus, String doctor_report, String video_consultancy_complete) {
            DoctorId = doctorId;
            BookingId = bookingId;
            Specialty = specialty;
            DoctorName = doctorName;
            BookingStatus = bookingStatus;
            PatientName = patientName;
            this.patientLocation = patientLocation;
            BookedDate = bookedDate;
            BookingTime = bookingTime;
            AppointmentTime = appointmentTime;
            AppointmentDate = appointmentDate;
            BookedServiceTime = bookedServiceTime;
            ClinicLocation = clinicLocation;
            TotalAmount = totalAmount;
            AmountStatus = amountStatus;
            this.doctor_report = doctor_report;
            this.video_consultancy_complete = video_consultancy_complete;
        }

        public String getDoctorId() {
            return DoctorId;
        }

        public void setDoctorId(String doctorId) {
            DoctorId = doctorId;
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

        public String getBookingTime() {
            return BookingTime;
        }

        public void setBookingTime(String bookingTime) {
            BookingTime = bookingTime;
        }

        public String getAppointmentTime() {
            return AppointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            AppointmentTime = appointmentTime;
        }

        public String getAppointmentDate() {
            return AppointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            AppointmentDate = appointmentDate;
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

        public String getDoctor_report() {
            return doctor_report;
        }

        public void setDoctor_report(String doctor_report) {
            this.doctor_report = doctor_report;
        }

        public String getVideo_consultancy_complete() {
            return video_consultancy_complete;
        }

        public void setVideo_consultancy_complete(String video_consultancy_complete) {
            this.video_consultancy_complete = video_consultancy_complete;
        }
    }


}
