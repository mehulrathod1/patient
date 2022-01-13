package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<SearchData> searchDataList = new ArrayList<>();

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

    public List<SearchData> getSearchDataList() {
        return searchDataList;
    }

    public void setSearchDataList(List<SearchData> searchDataList) {
        this.searchDataList = searchDataList;
    }

    public static class SearchData {

        public SearchData(String doctor_id, String doctor_name) {
            Doctor_id = doctor_id;
            Doctor_name = doctor_name;
        }

        public String getDoctor_id() {
            return Doctor_id;
        }

        public void setDoctor_id(String doctor_id) {
            Doctor_id = doctor_id;
        }

        public String getDoctor_name() {
            return Doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            Doctor_name = doctor_name;
        }

        @SerializedName("Doctor id")
        @Expose
        String Doctor_id;

        @SerializedName("Doctor Name")
        @Expose
        String Doctor_name;

    }
}
