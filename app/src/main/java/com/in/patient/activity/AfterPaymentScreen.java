package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.dialog;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.BookingConformationModel;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;
import com.razorpay.Checkout;

import net.gotev.uploadservice.BuildConfig;
import net.gotev.uploadservice.UploadServiceConfig;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterPaymentScreen extends AppCompatActivity {

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;
    File reportFile;

    TextView txtPayNow, txtDoctorName, txtBookingId, txtSpeciality, txtDName, txtBookingFor,
            txtBookingStatus, txtPatientName, txtAge, txtServiceTime, txtClinicAddress,
            texTotalAmount, txtAmountFees, txtAmountStatus, extDocument, txtReport, chat;

    String BookingId, doctorId, doctorFees;
    EditText comment;
    ImageView ProfileImage;

    Uri uri;

    String TAG = "BookAppointment";

    LinearLayout ll_download_report, ll_upload_doc, ll_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_paymentscreen);

        getSupportActionBar().hide();

        Checkout.preload(getApplicationContext());


        Intent intent = getIntent();
        BookingId = intent.getStringExtra("bookingId");
        doctorId = intent.getStringExtra("doctorId");
        Log.e("bookingId", "onCreate: " + doctorId + BookingId);
        init();

        getConformationDetail(Token, user_id, doctorId, BookingId);
    }

    public void init() {
        Glob.progressDialog(this);
        txtPayNow = findViewById(R.id.txtpayNow);


        txtDoctorName = findViewById(R.id.txtDoctorName);
        txtBookingId = findViewById(R.id.txtBookingId);
        txtSpeciality = findViewById(R.id.txtSpeciality);
        txtDName = findViewById(R.id.txtDName);
        txtBookingFor = findViewById(R.id.txtBookingFor);
        txtBookingStatus = findViewById(R.id.txtBookingStatus);
        txtPatientName = findViewById(R.id.txtPatientName);
        txtAge = findViewById(R.id.txtAge);
        txtServiceTime = findViewById(R.id.txtServiceTime);
        txtClinicAddress = findViewById(R.id.txtClinicAddress);
        texTotalAmount = findViewById(R.id.texTotalAmount);
        txtAmountFees = findViewById(R.id.txtAmountFees);
        txtAmountStatus = findViewById(R.id.txtAmountStatus);
        extDocument = findViewById(R.id.extDocument);
        txtReport = findViewById(R.id.txtReport);
        chat = findViewById(R.id.chat);
        comment = findViewById(R.id.comment);
        ProfileImage = findViewById(R.id.ProfileImage);

        ll_comment = findViewById(R.id.ll_comment);
        ll_upload_doc = findViewById(R.id.ll_upload_doc);
        ll_download_report = findViewById(R.id.ll_download_report);


        txtPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uri == null) {

                    Toast.makeText(getApplicationContext(), "Please Upload Report File", Toast.LENGTH_SHORT).show();
                } else {
//                    uploadDocument(Token, user_id, txtBookingId.getText().toString(), reportFile, comment.getText().toString());


                    Log.e("uriii", "onClick: " + uri.toString());
                    uploadFile();
                    finish();
                }
//                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                startActivity(intent);
            }
        });

        extDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

//                    Log.e("premitionnotgranted ", "onClick: " + "granted");
//
//
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    startActivityForResult(intent, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);


//                    try {
//                        Intent intent = new Intent();
//                        intent.setType("application/pdf");
//                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                        startActivityForResult(intent, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
//                    } catch (Exception e) {
//
//                    }

                } else {
                    ActivityCompat.requestPermissions(AfterPaymentScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                    Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
                }


            }
        });
    }

    public void getConformationDetail(String token, String user_id, String doctor_id, String booking_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getConformationDetail(token, user_id, doctor_id, booking_id).enqueue(new Callback<BookingConformationModel>() {
            @Override
            public void onResponse(Call<BookingConformationModel> call, Response<BookingConformationModel> response) {

                BookingConformationModel model = response.body();

                BookingConformationModel.ConformationData data = model.getData();

                Log.e("dataaa", "onResponse: " + data.getAmountStatus());
                Log.e("dataaa", "onResponse: " + data.getBookedServiceTime());
                Log.e("dataaa", "onResponse: " + data.getAmountStatus());
                Log.e("dataaa", "onResponse: " + data.getClinicLocation());
                Log.e("dataaa", "onResponse: " + data.getPatientDetails().getPatientName());
                Log.e("dataaa", "onResponse: " + data.getTotalAmount());


                Glide.with(getApplicationContext())
                        .load(data.getDoctorProfileImage())
                        .into(ProfileImage);


                txtDoctorName.setText(data.getDoctorName());
                txtBookingId.setText(data.getBookingId());
                txtSpeciality.setText(data.getSpecialty());
                txtDName.setText(data.getDoctorName());
                txtBookingFor.setText(data.getBookingFor());
                txtBookingStatus.setText(data.getBookingStatus());
                txtPatientName.setText(data.getPatientDetails().getPatientName());
                txtAge.setText(data.getPatientDetails().getPatientAge() + "  year");
                txtServiceTime.setText(data.getBookedServiceTime());
                txtClinicAddress.setText(data.getClinicLocation());
                texTotalAmount.setText(data.getTotalAmount());


                doctorFees = data.getTotalAmount();
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<BookingConformationModel> call, Throwable t) {

                Glob.dialog.dismiss();
            }
        });

    }

    public void uploadDocument(String token, String user_id, String booking_id, File documentfile, String comment) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

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
//                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
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

                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;

                            uri = imageUri;
                            Log.e("onActivityResult", "onActivityResult: " + uri);
//                            uploadFile();
                        }
                    } else if (data.getData() != null) {

                        uri = data.getData();
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        Log.e("onActivityResoult", "onActivityResult: " + uri);
//                        uploadFile();
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
                    .addParameter("booking_id", txtBookingId.getText().toString())
                    .addParameter("comments", comment.getText().toString())
                    .addParameter("token", Token);
            uploadRequest.startUpload();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}