package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointment extends AppCompatActivity {

    TextView txtPayNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        init();
        getConformationDetail(Token, "20", "1", "3");
    }

    public void init() {
        Glob.progressDialog(this);
        txtPayNow = findViewById(R.id.txtpayNow);

        txtPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingConformation(Token, user_id, "3");
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

                Glob.dialog.dismiss();


            }

            @Override
            public void onFailure(Call<BookingConformationModel> call, Throwable t) {

            }
        });

    }

    public void bookingConformation(String token, String user_id, String booking_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
//        Glob.dialog.show();


        call.bookingConformation(token, user_id, booking_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel commonModel = response.body();

                Toast.makeText(getApplicationContext(), "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

//                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });

    }
}