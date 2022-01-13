package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.BookingConformationModel;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointment extends AppCompatActivity implements PaymentResultListener {

    TextView txtPayNow, txtDoctorName, txtBookingId, txtSpeciality, txtDName, txtBookingFor,
            txtBookingStatus, txtPatientName, txtLocation, txtServiceTime, txtClinicAddress,
            texTotalAmount, txtAmountFees, txtAmountStatus, extDocument, txtReport;

    String BookingId, doctorId;

    String TAG = "BookAppointment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        getSupportActionBar().hide();

        Checkout.preload(getApplicationContext());


        Intent intent = getIntent();
        BookingId = intent.getStringExtra("bookingId");
        doctorId = intent.getStringExtra("doctorId");
        Log.e("bookingId", "onCreate: " + doctorId + BookingId);
        init();

        getConformationDetail(Token, user_id, doctorId, BookingId);
    }

    public void init() {
        Glob.progressDialog(this);
        txtPayNow = findViewById(R.id.txtpayNow);


        txtDoctorName = findViewById(R.id.txtDoctorName);
        txtBookingId = findViewById(R.id.txtBookingId);
        txtSpeciality = findViewById(R.id.txtSpeciality);
        txtDName = findViewById(R.id.txtDName);
        txtBookingFor = findViewById(R.id.txtBookingFor);
        txtBookingStatus = findViewById(R.id.txtBookingStatus);
        txtPatientName = findViewById(R.id.txtPatientName);
        txtLocation = findViewById(R.id.txtLocation);
        txtServiceTime = findViewById(R.id.txtServiceTime);
        txtClinicAddress = findViewById(R.id.txtClinicAddress);
        texTotalAmount = findViewById(R.id.texTotalAmount);
        txtAmountFees = findViewById(R.id.txtAmountFees);
        txtAmountStatus = findViewById(R.id.txtAmountStatus);
        extDocument = findViewById(R.id.extDocument);
        txtReport = findViewById(R.id.txtReport);


        txtPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookingConformation(Token, user_id, BookingId);
                startPayment();
            }
        });
    }


    public void getConformationDetail(String token, String user_id, String doctor_id, String booking_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getConformationDetail(token, user_id, doctor_id, booking_id).enqueue(new Callback<BookingConformationModel>() {
            @Override
            public void onResponse(Call<BookingConformationModel> call, Response<BookingConformationModel> response) {

                BookingConformationModel model = response.body();

                BookingConformationModel.ConformationData data = model.getData();


                Log.e("dataaa", "onResponse: " + data.getAmountStatus());
                Log.e("dataaa", "onResponse: " + data.getBookedServiceTime());
                Log.e("dataaa", "onResponse: " + data.getAmountStatus());
                Log.e("dataaa", "onResponse: " + data.getClinicLocation());
                Log.e("dataaa", "onResponse: " + data.getPatientName());
                Log.e("dataaa", "onResponse: " + data.getTotalAmount());


                txtDoctorName.setText(data.getDoctorName());
                txtBookingId.setText(data.getBookingId());
                txtSpeciality.setText(data.getSpecialty());
                txtDName.setText(data.getDoctorName());
                txtBookingFor.setText(data.getBookingFor());
                txtBookingStatus.setText(data.getBookingStatus());
                txtPatientName.setText(data.getPatientName());
                txtLocation.setText(data.getPatientLocation());
                txtServiceTime.setText(data.getBookedServiceTime());
                txtClinicAddress.setText(data.getClinicLocation());
                texTotalAmount.setText(data.getTotalAmount());
                txtAmountStatus.setText(data.getAmountStatus());
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<BookingConformationModel> call, Throwable t) {

            }
        });

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
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(getApplicationContext(), "Payment Successfull", Toast.LENGTH_SHORT).show();
        txtPayNow.setVisibility(View.GONE);

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}