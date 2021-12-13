package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.MyAppointmentAdapter;
import com.in.patient.model.MyAppointmentModel;

import java.util.ArrayList;
import java.util.List;

public class MyAppointments extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;


    MyAppointmentAdapter adapter;
    RecyclerView recyclerView;
    List<MyAppointmentModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        getSupportActionBar().hide();

        init();
        recyclerData();
    }


    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Appointment");


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void recyclerData() {

        MyAppointmentModel model = new MyAppointmentModel("9956328", "Lorem ipsum.", "Gujarat ", "$199", "", "Pending");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);

        adapter = new MyAppointmentAdapter(list, getApplicationContext(), new MyAppointmentAdapter.Click() {
            @Override
            public void onButtonClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }


}