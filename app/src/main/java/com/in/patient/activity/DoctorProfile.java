package com.in.patient.activity;

import static com.in.patient.globle.Glob.Base_Url;
import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.dialog;
import static com.in.patient.globle.Glob.user_id;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.patient.R;
import com.in.patient.adapter.DayAdapter;
import com.in.patient.adapter.DoctorTimePriceAdapter;
import com.in.patient.adapter.DoctorUploadedImageAdapter;
import com.in.patient.adapter.MyReviewAdapter;
import com.in.patient.adapter.SlotAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.AddBookingAppointmentModel;
import com.in.patient.model.CareAndCheckupModel;
import com.in.patient.model.ClinicImage;
import com.in.patient.model.DoctorProfileModel;
import com.in.patient.model.DoctorTimePrice;
import com.in.patient.model.MyReviewModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.Data;
import com.in.patient.retrofit.RetrofitClient;
import com.in.patient.retrofit.TimeSlotItem;
import com.in.patient.retrofit.TimeSlotModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfile extends AppCompatActivity {


    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;
    File reportFile;

    private String url = "https://www.google.com";
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";

    TextView doctorName, txtDoctorEducation, doctorSpeciality, languageSpoken, experience, txtClinicName, txtClinicLocation, txtFromDay, txtOpenCloseTime, txtFees, txtStatus, txtAbout, choose_file, view_all_review;
    EditText comment;
    RecyclerView recyclerView, reviewRecycler, timeRecycler, dayRecycler, slotRecycler;
    DoctorUploadedImageAdapter adapter;
    List<ClinicImage> clinicList = new ArrayList<>();

    MyReviewAdapter reviewAdapter;
    List<MyReviewModel> reviewList = new ArrayList<>();

    DoctorTimePriceAdapter doctorTimePriceAdapter;
    List<DoctorTimePrice> timePriceList = new ArrayList<>();


    SlotAdapter timeSlotAdapter;
    List<TimeSlotItem> timeSlotItemList = new ArrayList<>();

    Button bookAppointment;

    ImageView backButton, ProfileImage;

    String doctorId;
    String appointmentTime, appointmentDate;


    DayAdapter dayAdapter;
    List<String> dayList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        getSupportActionBar().hide();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 7; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            Log.i("cmvhclv", day);
            dateList.add(day);
            Log.e("datalisr", "onCreate: " + dateList.get(i));
        }

        SimpleDateFormat sddf = new SimpleDateFormat("EEEE");
        for (int i = 0; i < 7; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sddf.format(calendar.getTime());
            Log.i("cmvhclv", day);
            dayList.add(day);
            Log.e("datalist", "onCreate: " + dayList.get(i));


        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        appointmentDate = dateFormat.format(date);

        Log.e("current", "onCreate: " + appointmentDate);

        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        Log.e("id", "onCreate: " + doctorId);

        init();
        imageData();
        reviewData();
//        timePriceData();
        dayData();
        getDoctorProfile(Token, user_id, doctorId);
        getTimeSlot(Token, user_id, doctorId, appointmentDate);
    }

    public void init() {
        recyclerView = findViewById(R.id.recycler);
        reviewRecycler = findViewById(R.id.reviewRecycler);
        timeRecycler = findViewById(R.id.timeRecycler);
        dayRecycler = findViewById(R.id.dayRecycler);
        slotRecycler = findViewById(R.id.slotRecycler);

        bookAppointment = findViewById(R.id.BookAppointment);
        backButton = findViewById(R.id.backButton);
        ProfileImage = findViewById(R.id.ProfileImage);

        doctorName = findViewById(R.id.doctorName);
        txtDoctorEducation = findViewById(R.id.txtDoctorEducation);
        doctorSpeciality = findViewById(R.id.doctorSpeciality);
        languageSpoken = findViewById(R.id.languageSpoken);
        experience = findViewById(R.id.experience);
        txtClinicName = findViewById(R.id.txtClinicName);
        txtClinicLocation = findViewById(R.id.txtClinicLocation);
        txtFromDay = findViewById(R.id.txtFromDay);
        txtOpenCloseTime = findViewById(R.id.txtOpenCloseTime);
        txtStatus = findViewById(R.id.edtStatus);
        txtFees = findViewById(R.id.txtFees);
        txtAbout = findViewById(R.id.txtAbout);
        choose_file = findViewById(R.id.choose_file);
        comment = findViewById(R.id.comment);
        view_all_review = findViewById(R.id.view_all_review);
        view_all_review.setText(Html.fromHtml("<u>View All</u>"));


        Glob.progressDialog(this);

        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (appointmentTime == null) {
                    Toast.makeText(getApplicationContext(), "Please Select Appointment Time ", Toast.LENGTH_SHORT).show();
                } else if (comment.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please add comment", Toast.LENGTH_SHORT).show();
                } else {

                    Log.e("currentdata", "onClick: " + user_id + doctorId + appointmentDate + appointmentTime);

                    Log.e("appointmentTime", "onClick: " + appointmentTime);


                    String filename = String.valueOf(reportFile);
                    Log.e("appointmentTime", "onClick: " + reportFile);


                    if (filename.equals("null")) {
                        addBookingAppointment(Token, user_id, doctorId, appointmentDate, appointmentTime, "online", comment.getText().toString(), txtFees.getText().toString(), "");
                        Log.e("getName", "onClick: " + "null");
                    } else {
                        addBookingAppointmentWithReport(Token, user_id, doctorId, appointmentDate, appointmentTime, "online", comment.getText().toString(), txtFees.getText().toString(), reportFile);
                        Log.e("getName", "onClick: " + "notnull");
                    }

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("s", 2);
                startActivity(intent);
            }
        });

        choose_file.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Log.e("premitionnotgranted ", "onClick: " + "granted");


                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    startActivityForResult(intent, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);


                } else {
                    ActivityCompat.requestPermissions(DoctorProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                    Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
                }


            }
        });

        view_all_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyReview.class);
                intent.putExtra("variable", doctorId);
                startActivity(intent);
            }
        });

    }

    public void imageData() {

//        CareAndCheckupModel model = new CareAndCheckupModel("", "");
//        CareAndCheckupModel model1 = new CareAndCheckupModel("", "");
//        CareAndCheckupModel model2 = new CareAndCheckupModel("", "");
//        CareAndCheckupModel model3 = new CareAndCheckupModel("", "");
//        list.add(model);
//        list.add(model1);
//        list.add(model2);
//        list.add(model3);


        adapter = new DoctorUploadedImageAdapter(clinicList, getApplicationContext(), new DoctorUploadedImageAdapter.Click() {
            @Override
            public void onClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void reviewData() {

        MyReviewModel model = new MyReviewModel("", "Lorem ipsum.", "27/09/2021", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea.");
        reviewList.add(model);
        reviewList.add(model);
        reviewList.add(model);
        reviewList.add(model);
        reviewList.add(model);
        reviewList.add(model);
        reviewList.add(model);


        reviewAdapter = new MyReviewAdapter(reviewList, this, new MyReviewAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        reviewRecycler.setLayoutManager(mLayoutManager);
        reviewRecycler.setAdapter(reviewAdapter);
    }

    public void timePriceData() {


        DoctorTimePrice model = new DoctorTimePrice("9:00 - 9:30", "$259");
        timePriceList.add(model);
        timePriceList.add(model);
        timePriceList.add(model);
        timePriceList.add(model);
        timePriceList.add(model);
        timePriceList.add(model);
        timePriceList.add(model);


        doctorTimePriceAdapter = new DoctorTimePriceAdapter(timePriceList, this, new DoctorTimePriceAdapter.Click() {
            @Override
            public void itemClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        timeRecycler.setLayoutManager(mLayoutManager);
        timeRecycler.setAdapter(doctorTimePriceAdapter);
    }

    public void dayData() {


        dayAdapter = new DayAdapter(dayList, getApplicationContext(), new DayAdapter.Click() {
            @Override
            public void itemClick(int position) {

                appointmentDate = dateList.get(position);

                Log.e("appointmentDate", "itemClick: " + appointmentDate);

                getTimeSlot(Token, user_id, doctorId, appointmentDate);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dayRecycler.setLayoutManager(mLayoutManager);
        dayRecycler.setAdapter(dayAdapter);
    }
    public void getDoctorProfile(String token, String user_id, String doctor_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getDoctorProfile(token, user_id, doctor_id).enqueue(new Callback<DoctorProfileModel>() {
            @Override
            public void onResponse(Call<DoctorProfileModel> call, Response<DoctorProfileModel> response) {

                DoctorProfileModel model = response.body();


                List<ClinicImage> list = model.getData().getClinicImageList();

                for (int i = 0; i < list.size(); i++) {

                    ClinicImage clinicImage = list.get(i);

                    clinicList.add(clinicImage);

                }
                imageData();


                Glide.with(getApplicationContext())
                        .load(model.getData().getDoctorDetails().getProfile_image())
                        .into(ProfileImage);
                doctorName.setText(model.getData().getDoctorDetails().getDoctor_name());
                txtDoctorEducation.setText(model.getData().getDoctorDetails().getEducation());
                doctorSpeciality.setText(model.getData().getDoctorDetails().getSpecialist());
                languageSpoken.setText(model.getData().getDoctorDetails().getLanguage_spoken());
                experience.setText(model.getData().getDoctorDetails().getExperience() + "  year");
                txtAbout.setText(model.getData().getDoctorDetails().getAbout_me());
                txtClinicName.setText(model.getData().getClinicDetails().getClinicName());
                txtClinicLocation.setText(model.getData().getClinicDetails().getLocation());
                txtFromDay.setText(model.getData().getClinicDetails().getFromToDays());
                txtOpenCloseTime.setText(model.getData().getClinicDetails().getOpenCloseTime());
                txtFees.setText(model.getData().getClinicDetails().getOflineConsultancyFees());
                txtStatus.setText(model.getData().getClinicDetails().getDoctorAvailabilityStatus());

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<DoctorProfileModel> call, Throwable t) {
                dialog.dismiss();

            }
        });


    }

    public void addBookingAppointment(String token, String patient_id, String doctor_id, String booking_date, String slot_time, String booking_type,
                                      String comments, String fees, String report) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.addBookingAppointment(token, patient_id, doctor_id, booking_date, slot_time, booking_type, comments, fees, report).enqueue(new Callback<AddBookingAppointmentModel>() {
            @Override
            public void onResponse(Call<AddBookingAppointmentModel> call, Response<AddBookingAppointmentModel> response) {

                AddBookingAppointmentModel model = response.body();

                Log.e("booking id", "onResponse: " + model.getData().getBookingID());

                String booking_id = model.getData().getBookingID();
                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BookAppointment.class);
                intent.putExtra("bookingId", booking_id);
                intent.putExtra("doctorId", doctorId);
                startActivity(intent);


            }

            @Override
            public void onFailure(Call<AddBookingAppointmentModel> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("error", "onFailure: " + t.getMessage());
                dialog.dismiss();
            }
        });

    }


    public void addBookingAppointmentWithReport(String token, String patient_id, String doctor_id, String booking_date, String slot_time, String booking_type,
                                                String comments, String fees, File report) {


        RequestBody requestBody_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody requestBody_patient_id = RequestBody.create(MediaType.parse("multipart/form-data"), patient_id);
        RequestBody requestBody_doctor_id = RequestBody.create(MediaType.parse("multipart/form-data"), doctor_id);
        RequestBody requestBody_booking_date = RequestBody.create(MediaType.parse("multipart/form-data"), booking_date);
        RequestBody requestBody_slot_time = RequestBody.create(MediaType.parse("multipart/form-data"), slot_time);
        RequestBody requestBody_booking_type = RequestBody.create(MediaType.parse("multipart/form-data"), booking_type);
        RequestBody requestBody_comments = RequestBody.create(MediaType.parse("multipart/form-data"), comments);
        RequestBody requestBody_fees = RequestBody.create(MediaType.parse("multipart/form-data"), fees);

        MultipartBody.Part requestBody_report = null;
        RequestBody requestBody_req_report = RequestBody.create(MediaType.parse("multipart/form-data"), report);
        requestBody_report = MultipartBody.Part.createFormData("reportfile", reportFile.getName(), requestBody_req_report);


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.addBookingAppointmentWithReport(requestBody_token, requestBody_patient_id, requestBody_doctor_id,
                requestBody_booking_date, requestBody_slot_time,
                requestBody_booking_type, requestBody_comments, requestBody_fees, requestBody_report).enqueue(new Callback<AddBookingAppointmentModel>() {
            @Override
            public void onResponse(Call<AddBookingAppointmentModel> call, Response<AddBookingAppointmentModel> response) {

                AddBookingAppointmentModel model = response.body();

                Log.e("booking id", "onResponse: " + model.getData().getBookingID());

                String booking_id = model.getData().getBookingID();
                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BookAppointment.class);
                intent.putExtra("bookingId", booking_id);
                intent.putExtra("doctorId", doctorId);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<AddBookingAppointmentModel> call, Throwable t) {

                Log.e("alalalalaaall", "onFailure: " + t.getMessage());
                dialog.dismiss();

            }
        });

    }

    public void getTimeSlot(String token, String user_id, String doctor_id, String date) {

        Api call = RetrofitClient.getClient(Base_Url).create(Api.class);
        dialog.show();


        call.getTimeSlot(token, user_id, doctor_id, date).enqueue(new Callback<TimeSlotModel>() {
            @Override
            public void onResponse(Call<TimeSlotModel> call, Response<TimeSlotModel> response) {

                TimeSlotModel timeSlotModel = response.body();
                Data data = timeSlotModel.getData();
                List<TimeSlotItem> DataList = data.getTimeSlot();

                if (DataList.size() != 0) {
                    for (int i = 0; i < DataList.size(); i++) {
                        TimeSlotItem model = DataList.get(i);
                        TimeSlotItem timeData = new TimeSlotItem(model.getSlotTime(), model.getStatus());
                        Log.e("time", "onResponse: " + timeData.getSlotTime());
                        timeSlotItemList.add(timeData);
                    }
                    getTimeSlotData();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TimeSlotModel> call, Throwable t) {

            }
        });
    }

    public void getTimeSlotData() {

        timeSlotAdapter = new SlotAdapter(timeSlotItemList, getApplicationContext(), new SlotAdapter.Click() {
            @Override
            public void itemClick(int position) {

                appointmentTime = timeSlotItemList.get(position).getSlotTime();
                Log.e("appointmentTime", "itemClick: " + appointmentTime);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.HORIZONTAL, false);
        slotRecycler.setLayoutManager(mLayoutManager);
        slotRecycler.setAdapter(timeSlotAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra("s", 1);
//        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


            case 10:

                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file


                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                            Date());
                    reportFile = new File(getCacheDir(), "IMG_" + timeStamp + ".pdf");

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(reportFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.e("displayName", "onActivivvtyResult: " + displayName);
                                choose_file.setText(displayName);
                            }
                        } finally {
                            cursor.close();
                        }
//                    addBookingAppointmentWithReport(Token, user_id, doctorId, appointmentDate, appointmentTime, "online", comment.getText().toString(), txtFees.getText().toString(), reportFile);
//                    Uri uri = data.getData();
//                    String uriString = uri.toString();
//                    reportFile = new File(uriString);
//                    String path = reportFile.getAbsolutePath();
//                    String displayName = null;

//                    Log.e("path", "onActivityResult: " + reportFile);
//
//
//                    if (uriString.startsWith("content://")) {
//                        Cursor cursor = null;
//                        try {
//                            cursor = getContentResolver().query(uri, null, null, null, null);
//                            if (cursor != null && cursor.moveToFirst()) {
//                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                                Log.e("displayName", "onActivivvtyResult: " + displayName);
//                                choose_file.setText(displayName);
//                            }
//                        } finally {
//                            cursor.close();
//                        }
//                    } else if (uriString.startsWith("file://")) {
//                        displayName = reportFile.getName();
//                        Log.e("displayName", "onActivityResult: " + displayName);
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

    public File getPhotoFileUri(String fileName) {

        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "APP_TAG");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("APP_TAG", "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }


}