package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReportModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<ReportData> data = new ArrayList<>();

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

    public List<ReportData> getData() {
        return data;
    }

    public void setData(List<ReportData> data) {
        this.data = data;
    }

    public static class ReportData {

        @SerializedName("reportfile")
        @Expose
        String reportfile;

        public ReportData(String reportfile) {
            this.reportfile = reportfile;
        }

        public String getReportfile() {
            return reportfile;
        }

        public void setReportfile(String reportfile) {
            this.reportfile = reportfile;
        }
    }
}
