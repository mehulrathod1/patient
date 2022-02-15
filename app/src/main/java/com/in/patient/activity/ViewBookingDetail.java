package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.dialog;
import static com.in.patient.globle.Glob.user_id;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import com.in.patient.adapter.ReportAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.ReportModel;
import com.in.patient.model.SendNotificationModel;
import com.in.patient.model.ViewBookingDetailModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import net.gotev.uploadservice.BuildConfig;
import net.gotev.uploadservice.UploadServiceConfig;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBookingDetail extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    TextView txtUploadReport, txtReport, review_submit, add_review, chat, booking_idd, booking_date, booking_time,
            booking_status, payment_status, payment_amount, doctor_name,
            doctor_speciality, clinic_address, start_Video, appointment_time, appointment_date;


    LinearLayout ll_download_report;
    EditText review_text;
    RatingBar ratting;
    String date_and_time, doctor_id, onlyTime;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;
    File reportFile;
    File photoFile, img_file;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    public String photoFileName = "IMG_" + timeStamp + ".jpg";
    Uri uri;
    AlertDialog alert, reportAlert;
    AlertDialog.Builder reportAlertDialog, alertDialog;

    RecyclerView report_recycler;
    List<ReportModel.ReportData> reportDataList = new ArrayList<>();
    ReportAdapter reportAdapter;


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
        getViewBookingDetail(Token, user_id, id);
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
        appointment_time = findViewById(R.id.appointment_time);
        appointment_date = findViewById(R.id.appointment_date);
        txtUploadReport = findViewById(R.id.txtUpload);
        ll_download_report = findViewById(R.id.ll_download_report);

        alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.review_popup, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();


        reportAlertDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.report_popup, null);
        reportAlertDialog.setView(view);
        reportAlert = reportAlertDialog.create();
        report_recycler = view.findViewById(R.id.report_recycler);


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
                addReview(Token, user_id, doctor_id, review_text.getText().toString(), rate);
                alert.dismiss();

                Log.e("lengho", "onClick: " + user_id + "aaaaa" + doctor_id + "vvv" + rate);
            }
        });


        start_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNotification(Token, doctor_id, "test");

            }
        });

        txtReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getDoctorReport(Token, booking_idd.getText().toString(), user_id);
//                reportAlert.show();
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//
//                    Log.e("premitionnotgranted ", "onClick: " + "granted");
//
//                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(report_download));
//
//                    String title = URLUtil.guessFileName(report_download, null, null);
//                    request.setTitle(title);
//                    request.setDescription("Downloading file please wail.....");
//                    String cookie = CookieManager.getInstance().getCookie(report_download);
//                    request.addRequestHeader("cookie", cookie);
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
//
//                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                    downloadManager.enqueue(request);
//
//                    Toast.makeText(getApplicationContext(), "Downloading Started", Toast.LENGTH_SHORT).show();
//
//
//                } else {
//                    ActivityCompat.requestPermissions(ViewBookingDetail.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
//                    Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
//                }
            }
        });


        txtUploadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Intent intent = new Intent();
                        intent.setType("application/pdf");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        startActivityForResult(intent, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                    } catch (Exception e) {

                    }
                } else {
                    ActivityCompat.requestPermissions(ViewBookingDetail.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                    Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
                }
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
                booking_time.setText(data.getBookingTime());
                appointment_date.setText(data.getAppointmentDate());
                appointment_time.setText(data.getAppointmentTime());
                booking_status.setText(data.getBookingStatus());
                payment_status.setText(data.getAmountStatus());
                payment_amount.setText(data.getTotalAmount() + "₹");
                doctor_name.setText(data.getDoctorName());
                doctor_speciality.setText(data.getSpecialty());
                clinic_address.setText(data.getClinicLocation());
                doctor_id = data.getDoctorId();
//                date_and_time = data.getAppointmentDate() + " " + data.getAppointmentTime();
                date_and_time = data.getAppointmentDate();
                onlyTime = data.getBookedServiceTime();
                Glob.dialog.dismiss();


                Calendar compDate = Calendar.getInstance();
//                compDate.add(Calendar.MINUTE, 5);
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String getCurrentDateTime = sdf.format(compDate.getTime());
                String temp = "2022-02-03 12:10:00";// date_and_time

                Log.e("boosdfghjl", "onResponse: " + getCurrentDateTime + "-------" + date_and_time);

//                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                Date date1 = null;
                try {
                    date1 = formatter.parse(getCurrentDateTime);
                    Date date2 = formatter.parse(date_and_time);
                    Boolean bool1 = date1.after(date2);
                    Boolean bool2 = date1.before(date2);
                    Boolean bool3 = date1.equals(date2);

                    if (bool1) {
                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is after " + date_and_time));

                    } else if (bool2) {
                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is before " + date_and_time));

                    } else if (bool3) {
                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is equals to " + date_and_time));

                        Calendar compTime = Calendar.getInstance();
                        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                        String Time = sdfTime.format(compTime.getTime());
                        Log.e("checkTime", "onResponse: " + Time + "---------" + onlyTime);

                        int api_time = Integer.parseInt(onlyTime.split(":")[0]);
                        int api_time2 = Integer.parseInt(onlyTime.split(":")[1]);

                        int cur_time = Integer.parseInt(Time.split(":")[0]);
                        int cur_time2 = Integer.parseInt(Time.split(":")[1]);


                        int ap = api_time * 60 + api_time2;
                        int ct = cur_time * 60 + cur_time2;

                        Log.e("checkTime", "onResponse: " + ct + "----" + ap);

                        int def = ct - ap;

                        if (def >= -5 && def <= 20) {
                            Log.e("checkTime", "Visible: " + "Visible" + def);
                            start_Video.setVisibility(View.VISIBLE);
                        }
                        if (def>20){

                            Log.e("checkTime", "Visible: " + "Visible" + def);
                            chat.setVisibility(View.VISIBLE);
                            add_review.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();

                }
//                Calendar compDate = Calendar.getInstance();
//                compDate.add(Calendar.MINUTE, 5);
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                String getCurrentDateTime = sdf.format(compDate.getTime());
//                String temp = "2022-02-03 12:10:00";// date_and_time
//
//                Log.e("boosdfghjl", "onResponse: " + (date_and_time));
////                Log.e("demo", "onResponse: "+getCurrentDateTime.(temp)+ "---"+getCurrentDateTime +"-----" +temp);
//
//                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                //Parsing the given String to Date object
//                Date date1 = null;
//                try {
//                    date1 = formatter.parse(getCurrentDateTime);
//                    Date date2 = formatter.parse(date_and_time);
//                    Boolean bool1 = date1.after(date2);
//                    Boolean bool2 = date1.before(date2);
//                    Boolean bool3 = date1.equals(date2);
//
//                    if (bool1) {
//                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is after " + date_and_time));
//                        chat.setVisibility(View.VISIBLE);
//                        add_review.setVisibility(View.VISIBLE);
////                        start_Video.setClickable(false);
//                    } else if (bool2) {
//                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is before " + date_and_time));
//                    } else if (bool3) {
//                        Log.e("bool", "onResponse: " + (getCurrentDateTime + " is equals to " + date_and_time));
//                    }
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//
//
//                }
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

    public void getDoctorReport(String token, String bookingId, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getPatientReport(token, bookingId, user_id).enqueue(new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {

                ReportModel model = response.body();

                List<ReportModel.ReportData> DataList = model.getData();
                if (DataList != null) {
                    for (int i = 0; i < DataList.size(); i++) {
                        ReportModel.ReportData reportData = DataList.get(i);
                        ReportModel.ReportData data = new ReportModel.ReportData(reportData.getReportfile());

                        reportDataList.add(data);

                        Log.e("tagss", "onResponse: " + reportData.getReportfile());

                    }
                    reportData();
                    Glob.dialog.dismiss();
                    reportAlert.show();
                } else {
                    Glob.dialog.dismiss();
                    reportAlert.dismiss();
                    Toast.makeText(getApplicationContext(), "" + "No Report Found", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

                Glob.Channel_name = channel;

                Intent intent = new Intent(getApplicationContext(), VideoCallScreen.class);
                intent.putExtra("channel_name", channel);
                startActivity(intent);


                Log.e("asdfghjkjhgfdsa", "onResponse: " + channel);
                Log.e("id", "onResponse:" + data.getUser_id());

            }

            @Override
            public void onFailure(Call<SendNotificationModel> call, Throwable t) {

            }
        });
    }

    public void uploadDocument(String token, String user_id, String booking_id, File documentfile, String comment) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        dialog.show();


        RequestBody requestBody_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody requestBody_user_id = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody requestBody_booking_id = RequestBody.create(MediaType.parse("multipart/form-data"), booking_id);
        RequestBody requestBody_comment = RequestBody.create(MediaType.parse("multipart/form-data"), comment);


        MultipartBody.Part requestBody_report = null;
        RequestBody requestBody_req_report = RequestBody.create(MediaType.parse("multipart/form-data"), documentfile);
        requestBody_report = MultipartBody.Part.createFormData("reportfile", reportFile.getName(), requestBody_req_report);


        call.uploadDocument(requestBody_token, requestBody_user_id, requestBody_booking_id, requestBody_report, requestBody_comment).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


            case 10:


//                if (resultCode == RESULT_OK) {
//                    // Get the Uri of the selected file
//
//
////                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
////                            Date());
////                    reportFile = new File(getCacheDir(), "DOC_" + timeStamp + ".pdf");
////
////                    FileOutputStream fos = null;
////                    try {
////                        fos = new FileOutputStream(reportFile);
////                    } catch (FileNotFoundException e) {
////                        e.printStackTrace();
////                    }
////                    try {
////                        fos.flush();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    try {
////                        fos.close();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//
//
//                    Log.e("TAG", "onActivityResult: " + booking_idd.getText().toString());
//
//
//                    uri = data.getData();
//                    String uriString = uri.toString();
//                    reportFile = new File(uriString);
//                    String path = reportFile.getAbsolutePath();
////                    String displayName = null;
////                    Uri uri = data.getData();
////                    String uriString = uri.toString();
//
////                    uploadDocument(Token, user_id, booking_idd.getText().toString(), reportFile, "");
//
//                    if (uriString.startsWith("content://")) {
//                        Cursor cursor = null;
//                        try {
//                            cursor = getContentResolver().query(uri, null, null, null, null);
//                            if (cursor != null && cursor.moveToFirst()) {
//                                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                                Log.e("displayName", "onActivivvtyResult: " + displayName);
//                                txtUploadReport.setText(displayName);
//
//
//                                uriString = uri.getPath();
//                                reportFile = new File(uriString);
//                                uploadDocument(Token, user_id, booking_idd.getText().toString(), reportFile, "");
//                            }
//                        } finally {
//                            cursor.close();
//                        }
//                    }
//
//
//                }

                if (resultCode == RESULT_OK) {

                    uri = data.getData();
//                    uploadFile();
//                    Log.e("onActivityResult", "onActivityResult: " + uri);

                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;

                            uri = imageUri;
                            Log.e("onActivityResult", "onActivityResult: " + uri);
                            uploadFile();
                        }
                    } else if (data.getData() != null) {

                        uri = data.getData();
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        Log.e("onActivityResoult", "onActivityResult: " + uri);
                        uploadFile();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Granted.
                    Log.e("premitionnotgranted ", "onClick: " + "ggggg");

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
//                    startActivity(intent);
                    startActivityForResult(intent, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                } else {
                    Log.e("premitionnotgranted ", "onClick: " + "ddd");
                }
                break;
        }
    }

    public void uploadFile() {

        try {
            if (Build.VERSION.SDK_INT >= 26) {

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel = new NotificationChannel("TestChannel", "TestApp Channel", NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
            }

            UploadServiceConfig.initialize(getApplication(), "TestChannel", BuildConfig.DEBUG);

            MultipartUploadRequest uploadRequest = new MultipartUploadRequest(this, "http://ciam.notionprojects.tech/api/patient/upload_patient_document.php")
                    .setMethod("POST")
                    .addFileToUpload(uri.toString(), "reportfile")
                    .addParameter("user_id", user_id)
                    .addParameter("booking_id", booking_idd.getText().toString())
                    .addParameter("token", Token);

            uploadRequest.startUpload();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void reportData() {

        reportAdapter = new ReportAdapter(reportDataList, getApplicationContext(), new ReportAdapter.Click() {
            @Override
            public void onViewClick(int position) {


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reportDataList.get(position).getReportfile()));
                startActivity(browserIntent);
            }

            @Override
            public void onDownloadClick(int position) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Log.e("premitionnotgranted ", "onClick: " + "granted");

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(reportDataList.get(position).getReportfile()));
                    String title = URLUtil.guessFileName(reportDataList.get(position).getReportfile(), null, null);
                    request.setTitle(title);
                    request.setDescription("Downloading file please wail.....");
                    String cookie = CookieManager.getInstance().getCookie(reportDataList.get(position).getReportfile());
                    request.addRequestHeader("cookie", cookie);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(getApplicationContext(), "Downloading Started", Toast.LENGTH_SHORT).show();
                    reportAlert.dismiss();


                } else {
                    ActivityCompat.requestPermissions(ViewBookingDetail.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                    Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
                }


            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        report_recycler.setLayoutManager(mLayoutManager);
        report_recycler.setAdapter(reportAdapter);

    }

    public String timeConvert(int time) {
        return time / 24 / 60 + ":" + time / 60 % 24 + ':' + time % 60;
    }
}

