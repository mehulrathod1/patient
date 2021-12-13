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
import com.in.patient.adapter.MedicinesOrderAdapter;
import com.in.patient.model.LabTestModel;
import com.in.patient.model.MedicinesOrderModel;

import java.util.ArrayList;
import java.util.List;

public class MedicinesOrder extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    MedicinesOrderAdapter adapter;
    RecyclerView recyclerView;
    List<MedicinesOrderModel> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_order);
        getSupportActionBar().hide();

        init();
        recyclerData();
    }

    public void init() {


        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Medicine Orders");


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void recyclerData() {

        MedicinesOrderModel model = new MedicinesOrderModel("9956328", "27/09/2021", "Lorem ipsum dolor sit amet, consetetur.", "$199");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new MedicinesOrderAdapter(list, this, new MedicinesOrderAdapter.Click() {
            @Override
            public void ItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }


}