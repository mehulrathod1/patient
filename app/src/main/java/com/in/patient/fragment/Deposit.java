package com.in.patient.fragment;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.activity.AfterPaymentScreen;
import com.in.patient.globle.Glob;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import org.w3c.dom.Text;


public class Deposit extends Fragment {

    View view;
    TextView amount;
    Button proceed;
    String rupee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_deposit, container, false);
        init();
        return view;
    }

    public void init() {

        amount = view.findViewById(R.id.amount);
        proceed = view.findViewById(R.id.proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (amount.getText().toString().equals("")) {
                    amount.setError("Please Add Amount");
                } else {

                    Glob.total_deposit_amount = amount.getText().toString();
                    convertTotalAmount(amount.getText().toString());
                }

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

            checkout.open(getActivity(), options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    public void convertTotalAmount(String amount) {
        int convert = Integer.parseInt(amount);
        int a = convert * 100;
        amount = String.valueOf(a);
        startPayment(amount);

    }


}