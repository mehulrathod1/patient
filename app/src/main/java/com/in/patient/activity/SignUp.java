package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.in.patient.R;

public class SignUp extends AppCompatActivity {

    Button btnSignup;
    EditText EdtFirstName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();


        init();
    }


    public void init() {

        btnSignup = findViewById(R.id.btnSignup);
        EdtFirstName = findViewById(R.id.EdtFirstName);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);

            }
        });

        final String[] COUNTRIES = {"+91", "+92", "+93", "+93", "+94", "+92", "+93", "+93", "+94", "+92", "+93", "+93", "+94"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.countryCode);
        textView.setAdapter(adapter);
        textView.setText(COUNTRIES[0]);
    }

}