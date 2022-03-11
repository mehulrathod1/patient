package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.in.patient.R;
import com.in.patient.adapter.MyWalletAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.MyWalletModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;
import com.razorpay.PaymentResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWallet extends AppCompatActivity implements PaymentResultListener {

    ImageView nevBack, nevBackHeader;
    TextView headerTitle, myBalance;

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        getSupportActionBar().hide();
        init();
        getWalletBalance(Glob.Token, Glob.user_id);
    }

    public void init() {

        Glob.progressDialog(this);
        nevBack = findViewById(R.id.nevBack);
        nevBackHeader = findViewById(R.id.nevBackHeader);
        headerTitle = findViewById(R.id.header_title);

        myBalance = findViewById(R.id.myBalance);
        tabLayout = findViewById(R.id.tabb);
        viewPager = findViewById(R.id.pager);

        headerTitle.setText("My Wallet");


        tabLayout.addTab(tabLayout.newTab().setText("Wallet Transaction History"));
        tabLayout.addTab(tabLayout.newTab().setText("Request for Redemption"));
        tabLayout.addTab(tabLayout.newTab().setText("Deposit"));

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

    public void getWalletBalance(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.myWalletBalance(token, user_id).enqueue(new Callback<MyWalletModel>() {
            @Override
            public void onResponse(Call<MyWalletModel> call, Response<MyWalletModel> response) {


                MyWalletModel myWalletModel = response.body();
                myBalance.setText("₹ " + myWalletModel.getData().getWallet_balance());
                Glob.dialog.dismiss();


            }

            @Override
            public void onFailure(Call<MyWalletModel> call, Throwable t) {
                Glob.dialog.dismiss();

            }
        });

    }

    public void depositAmount(String token ,String  user_id,String amount,String payment_status) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.depositAmount(token,user_id,amount,payment_status).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel commonModel = response.body();
                Toast.makeText(getApplicationContext(), ""+commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                getWalletBalance(Glob.Token, Glob.user_id);
                Glob.total_deposit_amount = "";
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Glob.dialog.dismiss();
            }
        });


    }

    @Override
    public void onPaymentSuccess(String s) {
        try {


            depositAmount(Glob.Token,Glob.user_id,Glob.total_deposit_amount,"success");


        } catch (Exception e) {
            Log.e("TAGasdfghjk", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "Payment failed", Toast.LENGTH_SHORT).show();

    }
}