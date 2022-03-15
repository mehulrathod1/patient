package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.CouponAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CouponModel;
import com.in.patient.model.MyWalletModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabTestBooking extends AppCompatActivity {

    ImageView nevBack, cancelCoupon;
    TextView headerTitle, selectCoupon, couponName,grandTotal;
    Button payNow;
    RecyclerView couponRecycler;
    CouponAdapter couponAdapter;
    List<CouponModel.CouponList> couponList = new ArrayList<>();
    LinearLayout couponSelectedLayout, SelectCouponLayout;

    String totalWalletBalance;

    AlertDialog couponDialog;
    AlertDialog.Builder couponAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_booking);
        getSupportActionBar().hide();
        init();
        getWalletBalance(Glob.Token, Glob.user_id);

    }

    public void init() {
        Glob.progressDialog(this);
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        headerTitle.setText("Conform Order");

        selectCoupon = findViewById(R.id.selectCoupon);
        SelectCouponLayout = findViewById(R.id.SelectCouponLayout);
        couponSelectedLayout = findViewById(R.id.couponSelectedLayout);
        couponName = findViewById(R.id.couponName);
        cancelCoupon = findViewById(R.id.cancelCoupon);
        payNow = findViewById(R.id.payNow);
        grandTotal = findViewById(R.id.grandTotal);

        couponAlertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.coupon_poup_layout, null);
        couponAlertDialog.setView(dialogLayout);
        couponDialog = couponAlertDialog.create();

        couponRecycler = dialogLayout.findViewById(R.id.couponRecycler);


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selectCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCouponList(Token, user_id);

            }
        });
        cancelCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SelectCouponLayout.setVisibility(View.VISIBLE);
                couponSelectedLayout.setVisibility(View.GONE);

            }
        });

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int d ;

                try {
                   d = Integer.parseInt(totalWalletBalance);
                    Log.e("dfgh", "onClick: "+d);
                } catch(NumberFormatException nfe) {

                }

            }
        });
    }

    public void getCouponList(String token, String user_id) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getCouponList(token, user_id).enqueue(new Callback<CouponModel>() {
            @Override
            public void onResponse(Call<CouponModel> call, Response<CouponModel> response) {

                couponList.clear();
                CouponModel couponModel = response.body();
                List<CouponModel.CouponList> dataList = couponModel.getCouponLists();

                for (int i = 0; i < dataList.size(); i++) {

                    CouponModel.CouponList model = dataList.get(i);

                    CouponModel.CouponList data = new CouponModel.CouponList(model.getId(),
                            model.getTitle(), model.getCoupon_code(), model.getDiscount_type(),
                            model.getDiscount(), model.getFrom_date(), model.getTo_date(),
                            model.getUsage_limit());

                    couponList.add(data);
                }
                couponData();
                Glob.dialog.dismiss();
                couponDialog.show();
            }

            @Override
            public void onFailure(Call<CouponModel> call, Throwable t) {

            }
        });

    }

    public void couponData() {

        couponAdapter = new CouponAdapter(couponList, getApplicationContext(), new CouponAdapter.Click() {
            @Override
            public void onItemClick(int position) {

                String couponId = couponList.get(position).getId();
                String couponTitle = couponList.get(position).getTitle();
                String couponCode = couponList.get(position).getCoupon_code();


//                getBillSummary(Token, user_id, booking_id, couponId);

                SelectCouponLayout.setVisibility(View.GONE);
                couponSelectedLayout.setVisibility(View.VISIBLE);

                couponName.setText(couponTitle);
                couponDialog.dismiss();

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        couponRecycler.setLayoutManager(layoutManager);
        couponAdapter.notifyDataSetChanged();
        couponRecycler.setAdapter(couponAdapter);
    }

    public void getWalletBalance(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.myWalletBalance(token, user_id).enqueue(new Callback<MyWalletModel>() {
            @Override
            public void onResponse(Call<MyWalletModel> call, Response<MyWalletModel> response) {


                MyWalletModel myWalletModel = response.body();


                totalWalletBalance = myWalletModel.getData().getWallet_balance();
                Glob.dialog.dismiss();


            }

            @Override
            public void onFailure(Call<MyWalletModel> call, Throwable t) {
                Glob.dialog.dismiss();

            }
        });

    }


}