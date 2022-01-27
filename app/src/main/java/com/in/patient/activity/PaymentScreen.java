package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentScreen extends AppCompatActivity implements PaymentResultListener {

    Button pay_now;
    TextView total_fees, to_be_paid;

    String booking_id, total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        getSupportActionBar().hide();


        init();
    }


    public void init() {

        total_fees = findViewById(R.id.total_fees);
        to_be_paid = findViewById(R.id.to_be_paid);
        pay_now = findViewById(R.id.pay_now);


        Intent intent = getIntent();
        total_amount = intent.getStringExtra("amount");
        booking_id = intent.getStringExtra("booking_id");

        total_fees.setText(total_amount + " ₹");
        to_be_paid.setText(total_amount + " ₹");


        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPayment();
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
            options.put("amount", "100"); //300 * 100

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

        Toast.makeText(getApplicationContext(), "Payment Successfull", Toast.LENGTH_SHORT).show();
//        bookingConformation(Token, user_id, booking_id);
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();

    }

    public void bookingConformation(String token, String user_id, String booking_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.bookingConformation(token, user_id, booking_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel commonModel = response.body();

//                Toast.makeText(getApplicationContext(), "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Log.e("onDFailo", "onFailure: " + t.getMessage());

            }
        });

    }
}