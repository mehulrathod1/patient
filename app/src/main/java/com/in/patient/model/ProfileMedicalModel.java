package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileMedicalModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    ProfileMedical data;


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

    public ProfileMedical getData() {
        return data;
    }

    public void setData(ProfileMedical data) {
        this.data = data;
    }

    public class ProfileMedical {
        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("details_of_allergies")
        @Expose
        String details_of_allergies;

        @SerializedName("current_and_past_medication")
        @Expose
        String current_and_past_medication;

        @SerializedName("past_surgery_injury")
        @Expose
        String past_surgery_injury;

        @SerializedName("chronic_disease")
        @Expose
        String chronic_disease;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDetails_of_allergies() {
            return details_of_allergies;
        }

        public void setDetails_of_allergies(String details_of_allergies) {
            this.details_of_allergies = details_of_allergies;
        }

        public String getCurrent_and_past_medication() {
            return current_and_past_medication;
        }

        public void setCurrent_and_past_medication(String current_and_past_medication) {
            this.current_and_past_medication = current_and_past_medication;
        }

        public String getPast_surgery_injury() {
            return past_surgery_injury;
        }

        public void setPast_surgery_injury(String past_surgery_injury) {
            this.past_surgery_injury = past_surgery_injury;
        }

        public String getChronic_disease() {
            return chronic_disease;
        }

        public void setChronic_disease(String chronic_disease) {
            this.chronic_disease = chronic_disease;
        }
    }
}
