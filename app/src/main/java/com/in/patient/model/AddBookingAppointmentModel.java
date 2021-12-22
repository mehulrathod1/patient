package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBookingAppointmentModel {
    @SerializedName("status")
    @Expose
    Boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    AddBooking data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AddBooking getData() {
        return data;
    }

    public void setData(AddBooking data) {
        this.data = data;
    }

    public static class AddBooking {
        @SerializedName("patient_id")
        @Expose
        String patient_id;

        @SerializedName("doctor_id")
        @Expose
        String doctor_id;

        @SerializedName("bookingID")
        @Expose
        String bookingID;


        @SerializedName("booking_date")
        @Expose
        String booking_date;

        @SerializedName("booking_time")
        @Expose
        String booking_time;

        public String getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(String patient_id) {
            this.patient_id = patient_id;
        }

        public String getDoctor_id() {
            return doctor_id;
        }

        public void setDoctor_id(String doctor_id) {
            this.doctor_id = doctor_id;
        }

        public String getBookingID() {
            return bookingID;
        }

        public void setBookingID(String bookingID) {
            this.bookingID = bookingID;
        }

        public String getBooking_date() {
            return booking_date;
        }

        public void setBooking_date(String booking_date) {
            this.booking_date = booking_date;
        }

        public String getBooking_time() {
            return booking_time;
        }

        public void setBooking_time(String booking_time) {
            this.booking_time = booking_time;
        }
    }
}
