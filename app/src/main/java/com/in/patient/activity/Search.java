package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;

public class Search extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;
    TextView doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();
        init();

    }

    public void init() {
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        doctorName = findViewById(R.id.doctorName);


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("s", 1);
                startActivity(intent);
            }
        });
        doctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("s", 2);
                startActivity(intent);
            }
        });
    }

}
