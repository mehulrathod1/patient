package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.in.patient.R;
import com.in.patient.adapter.MyWalletAdapter;

public class MyWallet extends AppCompatActivity {

    ImageView nevBack, nevBackHeader;
    TextView headerTitle;

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        getSupportActionBar().hide();
        init();
    }

    public void init() {
        nevBack = findViewById(R.id.nevBack);
        nevBackHeader = findViewById(R.id.nevBackHeader);
        headerTitle = findViewById(R.id.header_title);


        tabLayout = findViewById(R.id.tabb);
        viewPager = findViewById(R.id.pager);

        headerTitle.setText("My Wallet");

        nevBack.setVisibility(View.GONE);

        tabLayout.addTab(tabLayout.newTab().setText("Wallet Transaction History"));
        tabLayout.addTab(tabLayout.newTab().setText("Request for Redemption"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


//        ProfileSettingAdapter profileSettingAdapter =new ProfileSettingAdapter(getChildFragmentManager(),getActivity(),tabLayout.getTabCount());
//        viewPager.setAdapter(profileSettingAdapter);
        MyWalletAdapter myWalletAdapter = new MyWalletAdapter(getSupportFragmentManager(), getApplicationContext(), tabLayout.getTabCount());
        viewPager.setAdapter(myWalletAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}