package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllTestModel {

    @SerializedName("status")
    @Expose
    Boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<TestData> labData = new ArrayList<>();

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

    public List<TestData> getLabData() {
        return labData;
    }

    public void setLabData(List<TestData> labData) {
        this.labData = labData;
    }

    public static class TestData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("test_name")
        @Expose
        String test_name;


        @SerializedName("test_description")
        @Expose
        String test_description;

        public TestData(String id, String test_name, String test_description) {
            this.id = id;
            this.test_name = test_name;
            this.test_description = test_description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTest_name() {
            return test_name;
        }

        public void setTest_name(String test_name) {
            this.test_name = test_name;
        }

        public String getTest_description() {
            return test_description;
        }

        public void setTest_description(String test_description) {
            this.test_description = test_description;
        }
    }
}
