package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.in.patient.R;

public class TermsAndCondition extends AppCompatActivity {


    Button Proceed;
    String booking_id, doctor_id, total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        total_amount = intent.getStringExtra("amount");
        booking_id = intent.getStringExtra("booking_id");
        doctor_id = intent.getStringExtra("doctor_id");


        init();
    }
    public void init(){

        Proceed = findViewById(R.id.Proceed);

        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PaymentScreen.class);
                intent.putExtra("amount", total_amount);
                intent.putExtra("booking_id", booking_id);
                intent.putExtra("doctor_id", doctor_id);
                startActivity(intent);
                finish();
            }
        });
    }
}