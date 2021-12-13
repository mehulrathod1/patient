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
import com.in.patient.adapter.MedicinesOrderAdapter;
import com.in.patient.adapter.OrderAdapter;
import com.in.patient.model.MedicinesOrderModel;
import com.in.patient.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrder extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    OrderAdapter adapter;
    RecyclerView recyclerView;
    List<OrderModel> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        getSupportActionBar().hide();

        init();
        recyclerData();

    }

    public void init() {


        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Orders");


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void recyclerData() {

        OrderModel model = new OrderModel("Product Name", "27/09/2021", "$199",
                "Deliverd", "9956328", "Paid", "Lorem ipsum dolor sit amet, consetetur.",
                "Lorem ipsum dolor sit amet, consetetur.");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new OrderAdapter(list, this, new OrderAdapter.Click() {
            @Override
            public void ItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}