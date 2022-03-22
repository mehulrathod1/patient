package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TestPackagesModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    List<PackagesData> packagesData = new ArrayList<>();

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

    public List<PackagesData> getPackagesData() {
        return packagesData;
    }

    public void setPackagesData(List<PackagesData> packagesData) {
        this.packagesData = packagesData;
    }


    public static class PackagesData {

        @SerializedName("packge_id")
        @Expose
        String package_id;

        @SerializedName("packge_name")
        @Expose
        String package_name;

        @SerializedName("lab_test")
        @Expose
        List<PackageTest> packageTestList = new ArrayList<>();

        public PackagesData(String package_id, String package_name) {
            this.package_id = package_id;
            this.package_name = package_name;
        }

        public String getPackage_id() {
            return package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public List<PackageTest> getPackageTestList() {
            return packageTestList;
        }

        public void setPackageTestList(List<PackageTest> packageTestList) {
            this.packageTestList = packageTestList;
        }

        public static class PackageTest {

            @SerializedName("test")
            @Expose
            String test;

            @SerializedName("test_name")
            @Expose
            String test_name;


            public PackageTest(String test, String test_name) {
                this.test = test;
                this.test_name = test_name;
            }

            public String getTest() {
                return test;
            }

            public void setTest(String test) {
                this.test = test;
            }

            public String getTest_name() {
                return test_name;
            }

            public void setTest_name(String test_name) {
                this.test_name = test_name;
            }
        }
    }
}
