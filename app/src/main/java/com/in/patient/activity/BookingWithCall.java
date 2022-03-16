package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.globle.Glob;

public class BookingWithCall extends AppCompatActivity {
    ImageView nevBack;
    TextView headerTitle,numberOne,numberTwo;
    int REQUEST_PHONE_CALL = 0;

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
        numberOne = findViewById(R.id.numberOne);
        numberTwo = findViewById(R.id.numberTwo);






        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        numberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(BookingWithCall.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                            BookingWithCall.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+"+918849938881"));
                    startActivity(callIntent);

                }

            }
        });
    }
}