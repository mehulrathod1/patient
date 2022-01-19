package com.in.patient.model;

public class SliderModel {

    String category_name,short_description,consultNow;

    int im_slider;

    public SliderModel(String category_name, String short_description, String consultNow, int im_slider) {
        this.category_name = category_name;
        this.short_description = short_description;
        this.consultNow = consultNow;
        this.im_slider = im_slider;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getConsultNow() {
        return consultNow;
    }

    public void setConsultNow(String consultNow) {
        this.consultNow = consultNow;
    }

    public int getIm_slider() {
        return im_slider;
    }

    public void setIm_slider(int im_slider) {
        this.im_slider = im_slider;
    }
}
