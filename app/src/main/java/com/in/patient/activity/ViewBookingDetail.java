package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.ViewBookingDetailModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBookingDetail extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    TextView booking_idd, booking_date, booking_time, booking_status, payment_status, payment_amount, doctor_name, doctor_speciality, clinic_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking_detail);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        init();

        getViewBookingDetail(Glob.Token,Glob.user_id,id);

    }

    public void init() {
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        booking_idd = findViewById(R.id.booking_id);
        booking_date = findViewById(R.id.booking_date);
        booking_time = findViewById(R.id.booking_time);
        booking_status = findViewById(R.id.booking_status);
        payment_status = findViewById(R.id.payment_status);
        payment_amount = findViewById(R.id.payment_amount);
        doctor_name = findViewById(R.id.doctor_name);
        doctor_speciality = findViewById(R.id.doctor_speciality);
        clinic_address = findViewById(R.id.clinic_address);


        Glob.progressDialog(this);
        headerTitle.setText("Booking Detail");


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyAppointments.class);
                startActivity(intent);
            }
        });

    }

    public void getViewBookingDetail(String token, String user_id, String booking_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getViewBookingDetail(token, user_id, booking_id).enqueue(new Callback<ViewBookingDetailModel>() {
            @Override
            public void onResponse(Call<ViewBookingDetailModel> call, Response<ViewBookingDetailModel> response) {

                ViewBookingDetailModel viewBookingDetailModel = response.body();

                ViewBookingDetailModel.BookingData data = viewBookingDetailModel.getBookingData();


                booking_idd.setText(data.getBookingId());
                        booking_date.setText(data.getBookedDate());
                booking_time.setText(data.getBookedServiceTime());
                        booking_status.setText(data.getBookedServiceTime());
                payment_status.setText(data.getAmountStatus());
                        payment_amount.setText(data.getTotalAmount() + "₹");
                doctor_name.setText(data.getDoctorName());
                        doctor_speciality.setText(data.getSpecialty());
                clinic_address.setText(data.getClinicLocation());
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<ViewBookingDetailModel> call, Throwable t) {

            }
        });
    }

}