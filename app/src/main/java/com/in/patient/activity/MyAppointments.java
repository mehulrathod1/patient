package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.ImsMmTelManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.MyAppointmentAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.MyAppointmentModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAppointments extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;


    MyAppointmentAdapter adapter;
    RecyclerView recyclerView;
    List<MyAppointmentModel.AppointmentData> list = new ArrayList<>();

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        getSupportActionBar().hide();

        init();
        getMyAppointment(Token, user_id);
    }


    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Appointment");

        Glob.progressDialog(this);


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void getMyAppointment(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getMyAppointment(token, user_id).enqueue(new Callback<MyAppointmentModel>() {
            @Override
            public void onResponse(Call<MyAppointmentModel> call, Response<MyAppointmentModel> response) {

                MyAppointmentModel myAppointmentModel = response.body();
                List<MyAppointmentModel.AppointmentData> DataList = myAppointmentModel.getMedicinesDataList();

                for (int i = 0; i < DataList.size(); i++) {
                    MyAppointmentModel.AppointmentData model = DataList.get(i);

                    MyAppointmentModel.AppointmentData data = new MyAppointmentModel.AppointmentData(
                            model.getBooingId(),
                            model.getDoctorName(),
                            model.getLocation(),
                            model.getStatus(),
                            model.getProfile(),
                            model.getFees(),
                            model.getFees()
                    );

                    list.add(data);
                }
                recyclerData();
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<MyAppointmentModel> call, Throwable t) {

            }
        });

    }

    public void recyclerData() {


        adapter = new MyAppointmentAdapter(list, getApplicationContext(), new MyAppointmentAdapter.Click() {
            @Override
            public void onButtonClick(int position) {

                id = list.get(position).getBooingId();
                Intent intent = new Intent(getApplicationContext(),ViewBookingDetail.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}