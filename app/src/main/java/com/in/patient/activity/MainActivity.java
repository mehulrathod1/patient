package com.in.patient.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.in.patient.R;
import com.in.patient.fragment.DoctorConsultSecond;
import com.in.patient.fragment.DoctorConsultant;
import com.in.patient.fragment.HomeDashboard;
import com.in.patient.fragment.Lab;
import com.in.patient.fragment.Medicines;

public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView Navigation;
    ImageView nevBack, nevBackHeader;
    FrameLayout firstFrame;
    View header;
    TextView header_title;
    DrawerLayout my_drawer_layout;
    CoordinatorLayout coordinator;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton search;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        init();
        fragment = new HomeDashboard();
        loadFragment(fragment);


        int index = getIntent().getIntExtra("s", 0);
        if (index == 1) {
            fragment = new DoctorConsultSecond();
            loadFragment(fragment);
        }
        if (index == 2) {

            fragment = new DoctorConsultSecond();
            loadFragment(fragment);

        }
    }

    public void init() {

        coordinator = findViewById(R.id.coordinator);
        Navigation = findViewById(R.id.Navigation);
        firstFrame = findViewById(R.id.firstFrame);
        header_title = findViewById(R.id.header_title);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        search = findViewById(R.id.Search);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);

        coordinator.setVisibility(View.VISIBLE);

        my_drawer_layout = findViewById(R.id.my_drawer_layout);

        header = findViewById(R.id.header);

        getSupportActionBar().hide();


        View headerLayout = Navigation.inflateHeaderView(R.layout.nev_header);
        nevBackHeader = headerLayout.findViewById(R.id.nevBackHeader);

        nevBack = findViewById(R.id.nevBack);


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        nevBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveNext(Search.class);

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        loadFragment(new HomeDashboard());
                        break;
                    case R.id.Revenue:
                        loadFragment(new DoctorConsultSecond());
                        break;
                    case R.id.Medicine:
                        loadFragment(new Medicines());
                        break;

                    case R.id.Profile:

                        loadFragment(new Lab());
                        break;
                }
                return true;
            }
        });

        Navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.ProfileSetting:
                        drawerLayout.closeDrawers();
                        moveNext(ProfileSetting.class);
                        break;
                    case R.id.MyAppointment:
                        drawerLayout.closeDrawers();
                        moveNext(MyAppointments.class);
                        break;

                    case R.id.MyChats:
                        drawerLayout.closeDrawers();
                        moveNext(Chat.class);
                        break;
                    case R.id.MyLabTest:
                        drawerLayout.closeDrawers();
                        moveNext(LabTest.class);
                        break;
                    case R.id.MyMedicineOrders:
                        drawerLayout.closeDrawers();
                        moveNext(MedicinesOrder.class);
                        break;
                    case R.id.MyOrder:
                        drawerLayout.closeDrawers();
                        moveNext(MyOrder.class);
                        break;
                    case R.id.MyReviewRating:
                        drawerLayout.closeDrawers();
                        moveNext(MyReview.class);
                        break;

                    case R.id.MYQuestion:
                        drawerLayout.closeDrawers();
                        moveNext(MyQuestion.class);
                        break;
                    case R.id.MyPrescriptions:
                        drawerLayout.closeDrawers();
                        moveNext(MyPrescriptions.class);
                        break;
                    case R.id.MyWallets:
                        drawerLayout.closeDrawers();
                        moveNext(MyWallet.class);
                        break;

                    case R.id.AccountSetting:
                        drawerLayout.closeDrawers();
                        moveNext(AccountSetting.class);
                        break;

                }
                return false;
            }
        });


    }

    public void moveNext(Class activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);

    }

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.firstFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}