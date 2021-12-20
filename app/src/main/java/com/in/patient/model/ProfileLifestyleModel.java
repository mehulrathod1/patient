package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileLifestyleModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    ProfileLifestyle data;

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

    public ProfileLifestyle getData() {
        return data;
    }

    public void setData(ProfileLifestyle data) {
        this.data = data;
    }

    public class ProfileLifestyle {

        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("smoking")
        @Expose
        String smoking;

        @SerializedName("alchol")
        @Expose
        String alcohol;

        @SerializedName("workout_level")
        @Expose
        String workout_level;

        @SerializedName("sports_involvement")
        @Expose
        String sports_involvement;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSmoking() {
            return smoking;
        }

        public void setSmoking(String smoking) {
            this.smoking = smoking;
        }

        public String getAlcohol() {
            return alcohol;
        }

        public void setAlcohol(String alcohol) {
            this.alcohol = alcohol;
        }

        public String getWorkout_level() {
            return workout_level;
        }

        public void setWorkout_level(String workout_level) {
            this.workout_level = workout_level;
        }

        public String getSports_involvement() {
            return sports_involvement;
        }

        public void setSports_involvement(String sports_involvement) {
            this.sports_involvement = sports_involvement;
        }
    }
}
