package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeCareModel {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<CareData> careDataList = new ArrayList<>();


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CareData> getCareDataList() {
        return careDataList;
    }

    public void setCareDataList(List<CareData> careDataList) {
        this.careDataList = careDataList;
    }

    public static class CareData {
        @SerializedName("Service_id")
        @Expose
        String Service_id;


        @SerializedName("Service Name")
        @Expose
        String ServiceName;

        @SerializedName("image")
        @Expose
        String image;


        public CareData(String service_id, String serviceName, String image) {
            Service_id = service_id;
            ServiceName = serviceName;
            this.image = image;
        }

        public String getService_id() {
            return Service_id;
        }

        public void setService_id(String service_id) {
            Service_id = service_id;
        }

        public String getServiceName() {
            return ServiceName;
        }

        public void setServiceName(String serviceName) {
            ServiceName = serviceName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
