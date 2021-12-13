package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.in.patient.R;

public class ChatDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dashboard);
        getSupportActionBar().hide();
    }
}