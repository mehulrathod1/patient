package com.in.patient.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.ViewBookingDetailModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

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

    TextView chat, booking_idd, booking_date, booking_time, booking_status, payment_status, payment_amount, doctor_name, doctor_speciality, clinic_address;


    String date_and_time;


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

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime noww = LocalDateTime.now();
        System.out.println(dtf.format(noww));
        Log.e("oriooo", "onCreate: " + dtf.format(noww));


//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            long parsedMillis = sdf.parse("2016-03-10 22:54:30").getTime();
//            long now = System.currentTimeMillis(); // 22:54:15
//
//            if (parsedMillis > now) {
//                Log.d("TAG", "In the future!");
//
//                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//                Intent intent = new Intent(this, MyActivity.class);
//                intent.putExtra("MyEXTRA", "From alarm");
//                PendingIntent broadcast = PendingIntent.getActivity(this, 0, intent, 0);
//                am.setExact(AlarmManager.RTC_WAKEUP, parsedMillis, broadcast);
//
//            } else {
//                Log.d("TAG", "In the past...");
//            }

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsed = sdf.parse("2022-02-03 10:49:20");

            Date now = new Date(System.currentTimeMillis()); // 2016-03-10 22:06:10
            Log.e("timetadf", "onCreate: " + parsed.compareTo(now));

            if (parsed.before(now)) {
                Log.e("button", "onCreate: " + "Gone");
                chat.setVisibility(View.GONE);
            }
            if (parsed.after(now)) {
                Log.e("button", "onCreate: " + date_and_time);
                chat.setVisibility(View.VISIBLE);
            }
            if (parsed == now) {
                Log.e("button", "onCreate: " + "same");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                booking_status.setText(data.getBookingStatus());
                payment_status.setText(data.getAmountStatus());
                payment_amount.setText(data.getTotalAmount() + "₹");
                doctor_name.setText(data.getDoctorName());
                doctor_speciality.setText(data.getSpecialty());
                clinic_address.setText(data.getClinicLocation());


                date_and_time = data.getBookedDate() + data.getBookedServiceTime();

                Log.e("button", "onCreate: " + date_and_time);

                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<ViewBookingDetailModel> call, Throwable t) {

            }
        });
    }


}