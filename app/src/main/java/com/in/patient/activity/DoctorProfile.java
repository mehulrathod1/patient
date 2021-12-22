package com.in.patient.activity;

import static com.in.patient.globle.Glob.Base_Url;
import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.dialog;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.DayAdapter;
import com.in.patient.adapter.DoctorTimePriceAdapter;
import com.in.patient.adapter.DoctorUploadedImageAdapter;
import com.in.patient.adapter.MyReviewAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.AddBookingAppointmentModel;
import com.in.patient.model.CareAndCheckupModel;
import com.in.patient.model.DoctorProfileModel;
import com.in.patient.model.DoctorTimePrice;
import com.in.patient.model.MyReviewModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.Data;
import com.in.patient.retrofit.RetrofitClient;
import com.in.patient.retrofit.TimeSlotItem;
import com.in.patient.retrofit.TimeSlotModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfile extends AppCompatActivity {


    TextView doctorName, txtDoctorEducation, doctorSpeciality, languageSpoken, experience, txtClinicName, txtClinicLocation, txtFromDay, txtOpenCloseTime, txtFees, txtStatus;

    RecyclerView recyclerView, reviewRecycler, timeRecycler, dayRecycler;
    DoctorUploadedImageAdapter adapter;
    List<CareAndCheckupModel> list = new ArrayList<>();

    MyReviewAdapter reviewAdapter;
    List<MyReviewModel> reviewList = new ArrayList<>();

    DoctorTimePriceAdapter doctorTimePriceAdapter;
    List<DoctorTimePrice> timePriceList = new ArrayList<>();

    Button bookAppointment;

    ImageView backButton;

    String doctorId;


    DayAdapter dayAdapter;
    List<String> dayList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        getSupportActionBar().hide();


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
            Log.e("datalisr", "onCreate: " + dayList.get(i));


        }

        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");


        Log.e("id", "onCreate: " + doctorId);

        init();
        imageData();
        reviewData();
        timePriceData();
        dayData();

        getDoctorProfile(Token, user_id, doctorId);

//        getTimeSlot(Token,user_id,id,"2021-12-13");

    }

    public void init() {
        recyclerView = findViewById(R.id.recycler);
        reviewRecycler = findViewById(R.id.reviewRecycler);
        timeRecycler = findViewById(R.id.timeRecycler);
        dayRecycler = findViewById(R.id.dayRecycler);

        bookAppointment = findViewById(R.id.BookAppointment);
        backButton = findViewById(R.id.backButton);


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


        Glob.progressDialog(this);

        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addBookingAppointment(Token, user_id, doctorId, "2021-12-13", "15:00:00");
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("s", 1);
                startActivity(intent);
            }
        });

    }

    public void imageData() {

        CareAndCheckupModel model = new CareAndCheckupModel("", "");
        CareAndCheckupModel model1 = new CareAndCheckupModel("", "");
        CareAndCheckupModel model2 = new CareAndCheckupModel("", "");
        CareAndCheckupModel model3 = new CareAndCheckupModel("", "");
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);


        adapter = new DoctorUploadedImageAdapter(list, getApplicationContext(), new DoctorUploadedImageAdapter.Click() {
            @Override
            public void onClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
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

                String s = dateList.get(position);

                Log.e("dfggf", "itemClick: " + s);

                getTimeSlot(Token, user_id, "13", "2021-12-13");

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


                doctorName.setText(model.getData().getDoctorDetails().getDoctor_name());
                txtDoctorEducation.setText(model.getData().getDoctorDetails().getEducation());
                doctorSpeciality.setText(model.getData().getDoctorDetails().getSpecialist());
                languageSpoken.setText(model.getData().getDoctorDetails().getLanguage_spoken());
                experience.setText(model.getData().getDoctorDetails().getExperience());

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

            }
        });


    }

    public void addBookingAppointment(String token, String patient_id, String doctor_id, String booking_date, String slot_time) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.addBookingAppointment(token, patient_id, doctor_id, booking_date, slot_time).enqueue(new Callback<AddBookingAppointmentModel>() {
            @Override
            public void onResponse(Call<AddBookingAppointmentModel> call, Response<AddBookingAppointmentModel> response) {

                AddBookingAppointmentModel model = response.body();

                Log.e("booking id", "onResponse: " + model.getData().getBookingID());

                String booking_id = model.getData().getBookingID();
                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BookAppointment.class);
                intent.putExtra("bookingId", booking_id);
                startActivity(intent);


            }

            @Override
            public void onFailure(Call<AddBookingAppointmentModel> call, Throwable t) {

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

                for (int i = 0; i < DataList.size(); i++) {

                    TimeSlotItem model = DataList.get(i);

                    TimeSlotItem timeData = new TimeSlotItem(model.getSlotTime(), model.getStatus());

                    Log.e("time", "onResponse: " + timeData.getSlotTime());

                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<TimeSlotModel> call, Throwable t) {

            }
        });

    }
}