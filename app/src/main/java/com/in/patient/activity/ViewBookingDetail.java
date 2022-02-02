package com.in.patient.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.SendNotificationModel;
import com.in.patient.model.ViewBookingDetailModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBookingDetail extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    TextView review_submit, add_review, chat, booking_idd, booking_date, booking_time, booking_status, payment_status, payment_amount, doctor_name, doctor_speciality, clinic_address, start_Video;


    EditText review_text;
    RatingBar ratting;
    String date_and_time, doctor_id;

    AlertDialog alert;
    AlertDialog.Builder alertDialog;

    public static final String inputFormat = "HH:mm";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking_detail);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        init();
        getViewBookingDetail(Glob.Token, Glob.user_id, id);


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
        chat = findViewById(R.id.chat);
        add_review = findViewById(R.id.add_review);
        start_Video = findViewById(R.id.start_Video);


        alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.review_popup, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();


        review_submit = dialogLayout.findViewById(R.id.review_submit);
        review_text = dialogLayout.findViewById(R.id.review_text);
        ratting = dialogLayout.findViewById(R.id.ratting);


        Glob.progressDialog(this);
        headerTitle.setText("Booking Detail");
        nevBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });

        review_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("rate", "init: " + ratting.getRating());

                String rate = String.valueOf(ratting.getRating());
                addReview(Glob.Token, Glob.user_id, doctor_id, review_text.getText().toString(), rate);
                alert.dismiss();

                Log.e("lengho", "onClick: " + Glob.user_id + "aaaaa" + doctor_id + "vvv" + rate);
            }
        });


        start_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNotification(Glob.Token, doctor_id, "test");
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
                booking_status.setText(data.getBookingStatus());
                payment_status.setText(data.getAmountStatus());
                payment_amount.setText(data.getTotalAmount() + "₹");
                doctor_name.setText(data.getDoctorName());
                doctor_speciality.setText(data.getSpecialty());
                clinic_address.setText(data.getClinicLocation());
                doctor_id = data.getDoctorId();
                date_and_time = data.getBookedDate() + data.getBookedServiceTime();
                Log.e("button", "onCreate: " + date_and_time);
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ViewBookingDetailModel> call, Throwable t) {

            }
        });
    }


    public void addReview(String token, String user_id, String doctor_id, String message, String ratting) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.addReview(token, user_id, doctor_id, message, ratting).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();

                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();

                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();


            }
        });

    }

    public void sendNotification(String token, String user_id, String message) {


        Api call = RetrofitClient.getClient("").create(Api.class);


        call.sendNotification(token, user_id, message).enqueue(new Callback<SendNotificationModel>() {
            @Override
            public void onResponse(Call<SendNotificationModel> call, Response<SendNotificationModel> response) {

                SendNotificationModel model = response.body();
                SendNotificationModel.SendNotification data = model.getData();

                Log.e("id", "onResdfghjponse:" + data.getChannelName());
                String channel = data.getChannelName();


                Intent intent = new Intent(getApplicationContext(), VideoCallScreen.class);
                intent.putExtra("channel_name", channel);
                startActivity(intent);


                Log.e("asdfghjkjhgfdsa", "onResponse: " + Glob.Channel_name);
                Log.e("id", "onResponse:" + data.getUser_id());
            }

            @Override
            public void onFailure(Call<SendNotificationModel> call, Throwable t) {

            }
        });
    }

}


//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime noww = LocalDateTime.now();
//        System.out.println(dtf.format(noww));
//        Log.e("oriooo", "onCreate: " + dtf.format(noww));
//
//        try {
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date parsed = sdf.parse("2022-02-01 10:36:20");
//
//            Date now = new Date(System.currentTimeMillis()); // 2016-03-10 22:06:10
//            Log.e("timetadf", "onCreate: " + parsed.compareTo(now));
//
//            if (parsed.before(now)) {
//                Log.e("button", "onCreate: " + "Gone");
//                chat.setVisibility(View.GONE);
//            }
//            if (parsed.after(now)) {
//                Log.e("button", "onCreate: " + "Visible");
//                chat.setVisibility(View.VISIBLE);
//            }
//            if (parsed.equals(now)) {
//                Log.e("button", "onCreate: " + "same");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }