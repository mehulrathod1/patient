package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.globle.Glob;

public class ViewLabBookingDetail extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle,add_review;
    AlertDialog alert;
    AlertDialog.Builder  alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lab_booking_detail);
        getSupportActionBar().hide();
        init();
    }

    public void init(){
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        add_review = findViewById(R.id.add_review);
        Glob.progressDialog(this);
        headerTitle.setText("Booking Detail");

        alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.review_popup, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();



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
    }

}