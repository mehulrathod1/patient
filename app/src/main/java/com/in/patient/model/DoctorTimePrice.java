package com.in.patient.model;

public class DoctorTimePrice {
    String Time, Price;

    public DoctorTimePrice(String time, String price) {
        Time = time;
        Price = price;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
