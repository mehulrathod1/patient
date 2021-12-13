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
import com.in.patient.adapter.LabTestAdapter;
import com.in.patient.adapter.MyAppointmentAdapter;
import com.in.patient.model.LabTestModel;
import com.in.patient.model.MyAppointmentModel;

import java.util.ArrayList;
import java.util.List;

public class LabTest extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    LabTestAdapter adapter;
    RecyclerView recyclerView;
    List<LabTestModel> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);
        getSupportActionBar().hide();

        init();
        recyclerData();
    }

    public void init() {


        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Lab Test");


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void recyclerData() {

        LabTestModel model = new LabTestModel("9956328", "27/09/2021", "$299", "Pending", "$199", "");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new LabTestAdapter(list, this, new LabTestAdapter.Click() {
            @Override
            public void ItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }


}