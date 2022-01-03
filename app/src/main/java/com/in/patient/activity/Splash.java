package com.in.patient.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.in.patient.R;

public class Splash extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    SharedPreferences prefs;
    String TAG = "Splash";
    String FMC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();




        prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        String id = prefs.getString("id", "null");//"No name defined" is the default value.
        String token = prefs.getString("token", "null");
        String auth = prefs.getString("auth", "null");
//        prefs.edit().remove("auth");
        prefs.edit().commit();


        Log.e(TAG, "onCreate: " + id);
        Log.e(TAG, "onCreate: " + token);
        Log.e(TAG, "onCreate: " + auth);

        if (auth.equals("null")) {
            moveNext(SignIn.class);

        }

        if (auth.equals("no")) {
            moveNext(MainActivity.class);
        }

        if (auth.equals("yes")) {
            moveNext(Authentication.class);


        }
    }



    public void moveNext(Class c) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), c);
                startActivity(i);
                finish();

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }




}