package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.adapter.CouponAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.BillSummaryModel;
import com.in.patient.model.CommonModel;
import com.in.patient.model.CouponModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentScreen extends AppCompatActivity implements PaymentResultListener {

    Button pay_now;
    TextView total_fees, to_be_paid, txtPatientName, txtPatientMobile, couponName, grandTotal, total_discount, selectCoupon;

    ImageView backButton, cancelCoupon;
    String booking_id, doctor_id, total_amount, totalPayAmount;

    LinearLayout couponSelectedLayout, SelectCouponLayout;

    RecyclerView couponRecycler;
    CouponAdapter couponAdapter;
    List<CouponModel.CouponList> couponList = new ArrayList<>();


    AlertDialog couponDialog;
    AlertDialog.Builder couponAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        getSupportActionBar().hide();

        init();
        getBillSummary(Token, user_id, booking_id, "");
    }

    public void init() {


        Glob.progressDialog(this);

        total_fees = findViewById(R.id.total_fees);
        to_be_paid = findViewById(R.id.to_be_paid);
        pay_now = findViewById(R.id.pay_now);
        backButton = findViewById(R.id.backButton);
        txtPatientName = findViewById(R.id.txtPatientName);
        txtPatientMobile = findViewById(R.id.txtPatientMobile);
        couponName = findViewById(R.id.couponName);
        grandTotal = findViewById(R.id.grandTotal);
        cancelCoupon = findViewById(R.id.cancelCoupon);
        total_discount = findViewById(R.id.total_discount);
        selectCoupon = findViewById(R.id.selectCoupon);
        SelectCouponLayout = findViewById(R.id.SelectCouponLayout);
        couponSelectedLayout = findViewById(R.id.couponSelectedLayout);

        Intent intent = getIntent();
        total_amount = intent.getStringExtra("amount");
        booking_id = intent.getStringExtra("booking_id");
        doctor_id = intent.getStringExtra("doctor_id");

//        total_fees.setText(total_amount + " ₹");
//        to_be_paid.setText(total_amount + " ₹");


        couponAlertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.coupon_poup_layout, null);
        couponAlertDialog.setView(dialogLayout);
        couponDialog = couponAlertDialog.create();

        couponRecycler = dialogLayout.findViewById(R.id.couponRecycler);

        selectCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCouponList(Token, user_id);
            }
        });


        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startPayment();
//                bookingConformation(Token, user_id, booking_id);

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getBillSummary(Token, user_id, booking_id, "");
                SelectCouponLayout.setVisibility(View.VISIBLE);
                couponSelectedLayout.setVisibility(View.GONE);

            }
        });

    }

    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_Wx4Pz8r5BYpqqQ");


        /**
         * Instantiate Checkout
         */

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.rectangle_1);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "1000"); //300 * 100

//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","9988776655");

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {


        try {

            addPaymentTransaction(Token, user_id, booking_id, total_amount, "success");
            bookingConformation(Token, user_id, booking_id);
            Toast.makeText(getApplicationContext(), "Payment Successfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), AfterPaymentScreen.class);
            intent.putExtra("bookingId", booking_id);
            intent.putExtra("doctorId", doctor_id);
            startActivity(intent);
            finish();


        } catch (Exception e) {
            Log.e("TAGasdfghjk", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
    }

    public void bookingConformation(String token, String user_id, String booking_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);


        call.bookingConformation(token, user_id, booking_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel commonModel = response.body();

//                Toast.makeText(getApplicationContext(), "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("onDFailo", "onFailure: " + commonModel.getMessage());


//                Intent intent = new Intent(getApplicationContext(), AfterPaymentScreen.class);
//                intent.putExtra("bookingId", booking_id);
//                intent.putExtra("doctorId", doctor_id);
//                startActivity(intent);
//                finish();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Log.e("onDFailo", "onFailure: " + t.getMessage());

            }
        });

    }

    public void addPaymentTransaction(String token, String user_id, String booking_id, String amount, String payment_status) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.addPaymentTransaction(token, user_id, booking_id, amount, payment_status).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }


    public void getBillSummary(String token, String user_id, String booking_id, String coupon_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getBillSummary(token, user_id, booking_id, coupon_id).enqueue(new Callback<BillSummaryModel>() {
            @Override
            public void onResponse(Call<BillSummaryModel> call, Response<BillSummaryModel> response) {

                BillSummaryModel billSummaryModel = response.body();

                BillSummaryModel.Summary data = billSummaryModel.getData();


                txtPatientName.setText(data.getPatient_name());
                txtPatientMobile.setText(data.getPatient_phone());
                total_fees.setText("₹ " + data.getConsultancy_fees());
                total_discount.setText("₹ " + data.getCoupon_discount());
                to_be_paid.setText("₹ " + data.getTo_be_paid());

                grandTotal.setText("₹ " + data.getTo_be_paid());

                totalPayAmount = data.getTo_be_paid();
                Glob.dialog.dismiss();
            }


            @Override
            public void onFailure(Call<BillSummaryModel> call, Throwable t) {

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


                getBillSummary(Token, user_id, booking_id, couponId);

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


}