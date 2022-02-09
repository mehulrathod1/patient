package com.in.patient.model;

public class ReviewSliderModel {

    String user_name,review_date,review_text;

    int im_slider;


    public ReviewSliderModel(String user_name, String review_date, String review_text, int im_slider) {
        this.user_name = user_name;
        this.review_date = review_date;
        this.review_text = review_text;
        this.im_slider = im_slider;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public int getIm_slider() {
        return im_slider;
    }

    public void setIm_slider(int im_slider) {
        this.im_slider = im_slider;
    }
}
