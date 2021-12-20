package com.in.patient.retrofit;


import com.in.patient.model.MedicinesModel;
import com.in.patient.model.MyAppointmentModel;
import com.in.patient.model.ProfileLifestyleModel;
import com.in.patient.model.ProfileMedicalModel;
import com.in.patient.model.ProfilePersonalModel;
import com.in.patient.model.SignInModel;
import com.in.patient.model.SignUpModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("signup.php")
    Call<SignUpModel> signUp(

            @Field("token") String token,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("country_code") String country_code,
            @Field("mobile_no") String mobile_no,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password
    );


    @FormUrlEncoded
    @POST("login.php")
    Call<SignInModel> signIn(
            @Field("token") String token,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("get_patient_profile.php")
    Call<ProfilePersonalModel> getProfilePersonal(
            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("get_patient_medical.php")
    Call<ProfileMedicalModel> getProfileMedical(

            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("get_patient_lifestyle.php")
    Call<ProfileLifestyleModel> getProfileLifestyle(

            @Field("token") String token,
            @Field("user_id") String user_id

    );


    @FormUrlEncoded
    @POST("get_medicines_list.php")
    Call<MedicinesModel> getMedicines(
            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
        @POST("my_appointment.php")
    Call<MyAppointmentModel> getMyAppointment(
            @Field("token") String token,
            @Field("user_id") String user_id

    );
}
