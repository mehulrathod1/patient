package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.globle.Glob;

public class BookingWithCall extends AppCompatActivity {
    ImageView nevBack;
    TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_with_call);
        getSupportActionBar().hide();
        init();
    }

    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        headerTitle.setText("Contact Number");
        Glob.progressDialog(this);

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}