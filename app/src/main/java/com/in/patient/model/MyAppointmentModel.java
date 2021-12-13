package com.in.patient.model;

public class MyAppointmentModel {
    String BookingId, DoctorName, CityName, Price, profileImage, Status;

    public MyAppointmentModel(String bookingId, String doctorName, String cityName, String price, String profileImage, String status) {
        BookingId = bookingId;
        DoctorName = doctorName;
        CityName = cityName;
        Price = price;
        this.profileImage = profileImage;
        Status = status;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
