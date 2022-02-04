package com.in.patient.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    TextView txtReport, review_submit, add_review, chat, booking_idd, booking_date, booking_time, booking_status, payment_status, payment_amount, doctor_name, doctor_speciality, clinic_address, start_Video;

    LinearLayout ll_download_report;

    EditText review_text;
    RatingBar ratting;
    String date_and_time, doctor_id, report_download;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;

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


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -15);


//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        String getCurrentDateTime = sdf.format(c.getTime());
//        String getMyTime = "05/19/2016 09:45";
//        Log.e("getCurrentDateTime", getCurrentDateTime);
//
//        if (getCurrentDateTime.compareTo(getMyTime) < 0) {
//
//        } else {
//            Log.e("Return", "getMyTime older than getCurrentDateTime ");
//        }

        Log.e("Tiemeeee", "onCreate: " + " " + date_and_time);


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
        txtReport = findViewById(R.id.txtReport);

        ll_download_report = findViewById(R.id.ll_download_report);

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

                sendNotification(Glob.Token, "25", "test");

            }
        });

        txtReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Log.e("premitionnotgranted ", "onClick: " + "granted");

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(report_download));

                    String title = URLUtil.guessFileName(report_download, null, null);
                    request.setTitle(title);
                    request.setDescription("Downloading file please wail.....");
                    String cookie = CookieManager.getInstance().getCookie(report_download);
                    request.addRequestHeader("cookie", cookie);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(getApplicationContext(), "Downloading Started", Toast.LENGTH_SHORT).show();


                } else {
                    ActivityCompat.requestPermissions(ViewBookingDetail.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                    Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
                }


//                manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                Uri uri = Uri.parse(report_download);
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//                long reference = manager.enqueue(request);

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
                date_and_time = data.getBookedDate() + " " + data.getBookedServiceTime();
                report_download = data.getDoctor_report();

                Log.e("button", "onCreate: " + " " + report_download);
                if (report_download.equals("")) {

                    ll_download_report.setVisibility(View.GONE);
                }
                Glob.dialog.dismiss();


                Calendar compDate = Calendar.getInstance();
                compDate.add(Calendar.MINUTE, 5);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getCurrentDateTime = sdf.format(compDate.getTime());
                String temp = "2022-02-03 12:10:00";// date_and_time

                Log.e("boosdfghjl", "onResponse: " + (date_and_time));
//                Log.e("demo", "onResponse: "+getCurrentDateTime.(temp)+ "---"+getCurrentDateTime +"-----" +temp);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //Parsing the given String to Date object
                Date date1 = null;
                try {
                    date1 = formatter.parse(getCurrentDateTime);
                    Date date2 = formatter.parse(date_and_time);
                    Boolean bool1 = date1.after(date2);
                    Boolean bool2 = date1.before(date2);
                    Boolean bool3 = date1.equals(date2);

                    if (bool1) {
                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is after " + date_and_time));
                        chat.setVisibility(View.VISIBLE);
                        add_review.setVisibility(View.VISIBLE);
                    } else if (bool2) {
                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is before " + date_and_time));
                    } else if (bool3) {
                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is equals to " + date_and_time));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();


                }


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


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);


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

