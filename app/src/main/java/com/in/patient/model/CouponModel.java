package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CouponModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<CouponList> couponLists = new ArrayList<>();

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

    public List<CouponList> getCouponLists() {
        return couponLists;
    }

    public void setCouponLists(List<CouponList> couponLists) {
        this.couponLists = couponLists;
    }

    public static class CouponList {


        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("title")
        @Expose
        String title;

        @SerializedName("coupon_code")
        @Expose
        String coupon_code;

        @SerializedName("discount_type")
        @Expose
        String discount_type;

        @SerializedName("discount")
        @Expose
        String discount;

        @SerializedName("from_date")
        @Expose
        String from_date;

        @SerializedName("to_date")
        @Expose
        String to_date;

        @SerializedName("usage_limit")
        @Expose
        String usage_limit;

        public CouponList(String id, String title, String coupon_code, String discount_type, String discount, String from_date, String to_date, String usage_limit) {
            this.id = id;
            this.title = title;
            this.coupon_code = coupon_code;
            this.discount_type = discount_type;
            this.discount = discount;
            this.from_date = from_date;
            this.to_date = to_date;
            this.usage_limit = usage_limit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(String coupon_code) {
            this.coupon_code = coupon_code;
        }

        public String getDiscount_type() {
            return discount_type;
        }

        public void setDiscount_type(String discount_type) {
            this.discount_type = discount_type;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getFrom_date() {
            return from_date;
        }

        public void setFrom_date(String from_date) {
            this.from_date = from_date;
        }

        public String getTo_date() {
            return to_date;
        }

        public void setTo_date(String to_date) {
            this.to_date = to_date;
        }

        public String getUsage_limit() {
            return usage_limit;
        }

        public void setUsage_limit(String usage_limit) {
            this.usage_limit = usage_limit;
        }
    }
}
