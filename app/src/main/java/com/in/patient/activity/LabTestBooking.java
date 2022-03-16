package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.in.patient.model.CommonModel;
import com.in.patient.model.CouponModel;
import com.in.patient.model.MyWalletModel;
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

public class LabTestBooking extends AppCompatActivity implements PaymentResultListener {

    ImageView nevBack, cancelCoupon;
    TextView headerTitle, selectCoupon, couponName, grandTotal, payWithRazorpay, payWithWallet, cancel,myBalance;
    Button payNow;
    RecyclerView couponRecycler;
    CouponAdapter couponAdapter;
    List<CouponModel.CouponList> couponList = new ArrayList<>();
    LinearLayout couponSelectedLayout, SelectCouponLayout;

    String totalWalletBalance;

    AlertDialog couponDialog, paymentDialog;
    AlertDialog.Builder couponAlertDialog, PaymentAlertDialog;
    AlertDialog.Builder builder;


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
        builder = new AlertDialog.Builder(this);

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

        PaymentAlertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater1 = getLayoutInflater();
        View dialog = inflater1.inflate(R.layout.select_payment_method_popup, null);
        PaymentAlertDialog.setView(dialog);
        paymentDialog = PaymentAlertDialog.create();
        payWithRazorpay = dialog.findViewById(R.id.payWithRazorpay);
        payWithWallet = dialog.findViewById(R.id.payWithWallet);
        cancel = dialog.findViewById(R.id.cancel);
        myBalance = dialog.findViewById(R.id.myBalance);


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

//                float f = Float.parseFloat(totalWalletBalance);
//                int i = Math.round(f);
//                totalWalletBalance = String.valueOf(i);
//                String total = grandTotal.getText().toString();
//
//                if (Integer.parseInt(total) < )

                paymentDialog.show();
                myBalance.setText(totalWalletBalance);

            }
        });

        payWithRazorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                convertTotalAmount(grandTotal.getText().toString());

            }
        });

        payWithWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float WalletAmountInFloat = Float.parseFloat(totalWalletBalance);
                int WalletAmountInInt = Math.round(WalletAmountInFloat);
                totalWalletBalance = String.valueOf(WalletAmountInInt);

                String payableAmount = grandTotal.getText().toString();
                float payableAmountInFloat = Float.parseFloat(payableAmount);
                int payableAmountInInt = Math.round(payableAmountInFloat);
                payableAmount = String.valueOf(payableAmountInInt);


                if (Integer.parseInt(payableAmount) > Integer.parseInt(totalWalletBalance)) {

                    Toast.makeText(getApplicationContext(), "Not Enough Balance in your Wallet", Toast.LENGTH_SHORT).show();
                } else {

//                    walletPayment(Token, user_id, payableAmount);

                    builder.setMessage(R.string.Booking_Id) .setTitle(R.string.Email_id);
                    builder.setMessage("Continue payment with Wallet ?")
                            .setCancelable(false)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Wallet Payment");
                    alert.show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
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

    public void walletPayment(String token, String user_id, String amount) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.payWithWallet(token, user_id, amount).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {


                CommonModel commonModel = response.body();

                Toast.makeText(getApplicationContext(), "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

                Glob.dialog.dismiss();
                paymentDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }


    public void startPayment(String amount) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(Glob.razorpayKeyId);


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
            options.put("amount", amount); //300 * 100

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

    public void convertTotalAmount(String amount) {
        float payableAmountInFloat = Float.parseFloat(amount);
        int convert = Math.round(payableAmountInFloat);
        int a = convert * 100;
        amount = String.valueOf(a);
        startPayment(amount);

    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}