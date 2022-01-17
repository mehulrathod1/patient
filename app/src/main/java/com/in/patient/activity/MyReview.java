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
import com.in.patient.adapter.MyReviewAdapter;
import com.in.patient.model.MyReviewModel;

import java.util.ArrayList;
import java.util.List;

public class MyReview extends AppCompatActivity {


    ImageView nevBack;
    TextView headerTitle;
    RecyclerView recyclerView;
    MyReviewAdapter adapter;
    List<MyReviewModel> list = new ArrayList<>();
    String variable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        variable= intent.getStringExtra("variable");


        init();
        recyclerData();
    }

    public void init() {
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Review");

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (variable.equals("doctor_profile")){

                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void recyclerData() {

        MyReviewModel model = new MyReviewModel("", "Lorem ipsum.", "27/09/2021", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea.");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new MyReviewAdapter(list, this, new MyReviewAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}