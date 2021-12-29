package com.in.patient.retrofit;


import com.in.patient.model.AddBookingAppointmentModel;
import com.in.patient.model.BookingConformationModel;
import com.in.patient.model.CommonModel;
import com.in.patient.model.DoctorConsultantSecondModel;
import com.in.patient.model.DoctorProfileModel;
import com.in.patient.model.FindDoctorModel;
import com.in.patient.model.MedicineOrderListModel;
import com.in.patient.model.MedicinesModel;
import com.in.patient.model.MyAppointmentModel;
import com.in.patient.model.ProductModel;
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
    @POST("update_patient_medical.php")
    Call<CommonModel> updateProfileMedical(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("details_of_allergies") String details_of_allergies,
            @Field("current_and_past_medication") String current_and_past_medication,
            @Field("past_surgery_injury") String past_surgery_injury,
            @Field("chronic_disease") String chronic_disease


    );

    @FormUrlEncoded
    @POST("get_patient_lifestyle.php")
    Call<ProfileLifestyleModel> getProfileLifestyle(

            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("update_patient_lifestyle.php")
    Call<CommonModel> updateProfileLifestyle(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("smoking") String smoking,
            @Field("alchol") String alchol,
            @Field("workout_level") String workout_level,
            @Field("sports_involvement") String sports_involvement

    );

    @FormUrlEncoded
    @POST("get_medicines_list.php")
    Call<MedicinesModel> getMedicines(
            @Field("token") String token,
            @Field("user_id") String user_id

    );


    @FormUrlEncoded
    @POST("add_to_cart_medicines.php")
    Call<CommonModel> addToCartMedicines(
            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("medicine_id") String medicine_id,
            @Field("product_qty") String product_qty
    );

    @FormUrlEncoded
    @POST("get_medicine_order_list.php")
    Call<MedicineOrderListModel> getMedicineOrderList(
            @Field("token") String token,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("my_appointment.php")
    Call<MyAppointmentModel> getMyAppointment(
            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("get_doctor_specialisties.php")
    Call<FindDoctorModel> getDoctorSpecialist(

            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("get_doctors.php")
    Call<DoctorConsultantSecondModel> getDoctor(
            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("doctor_profile_1.php")
    Call<DoctorProfileModel> getDoctorProfile(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("doctor_id") String doctor_id
    );

    @FormUrlEncoded
    @POST("time_slot.php")
    Call<TimeSlotModel> getTimeSlot(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("doctor_id") String doctor_id,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST("get_confirmation_booking_details.php")
    Call<BookingConformationModel> getConformationDetail(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("doctor_id") String doctor_id,
            @Field("booking_id") String booking_id
    );


    @FormUrlEncoded
    @POST("add_booking_confirmation.php")
    Call<CommonModel> bookingConformation(
            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("booking_id") String booking_id
    );


    @FormUrlEncoded
    @POST("add_booking_appointment.php")
    Call<AddBookingAppointmentModel> addBookingAppointment(

            @Field("token") String token,
            @Field("patient_id") String patient_id,
            @Field("doctor_id") String doctor_id,
            @Field("booking_date") String booking_date,
            @Field("slot_time") String slot_time
    );


    @FormUrlEncoded
    @POST("get_helthcare_and_product.php")
    Call<ProductModel> getProduct(
            @Field("token") String token,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("add_to_cart_products.php")
    Call<CommonModel> addToCartProduct(
            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Field("product_qty") String product_qty
    );


    @FormUrlEncoded
    @POST("account_setting.php")
    Call<CommonModel> accountSetting(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );
}
