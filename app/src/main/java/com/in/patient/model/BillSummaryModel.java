package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillSummaryModel {

    @SerializedName("status")
    @Expose
    Boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    Summary data;

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

    public Summary getData() {
        return data;
    }

    public void setData(Summary data) {
        this.data = data;
    }

    public class Summary {
        @SerializedName("patient_name")
        @Expose
        String patient_name;

        @SerializedName("patient_phone")
        @Expose
        String patient_phone;

        @SerializedName("consultancy_fees")
        @Expose
        String consultancy_fees;

        @SerializedName("coupon_discount")
        @Expose
        String coupon_discount;

        @SerializedName("to_be_paid")
        @Expose
        String to_be_paid;




        public String getPatient_name() {
            return patient_name;
        }

        public void setPatient_name(String patient_name) {
            this.patient_name = patient_name;
        }

        public String getPatient_phone() {
            return patient_phone;
        }

        public void setPatient_phone(String patient_phone) {
            this.patient_phone = patient_phone;
        }

        public String getConsultancy_fees() {
            return consultancy_fees;
        }

        public void setConsultancy_fees(String consultancy_fees) {
            this.consultancy_fees = consultancy_fees;
        }

        public String getCoupon_discount() {
            return coupon_discount;
        }

        public void setCoupon_discount(String coupon_discount) {
            this.coupon_discount = coupon_discount;
        }

        public String getTo_be_paid() {
            return to_be_paid;
        }

        public void setTo_be_paid(String to_be_paid) {
            this.to_be_paid = to_be_paid;
        }
    }
}
