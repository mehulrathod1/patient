package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.MyReviewAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.MyReviewModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReview extends AppCompatActivity {


    ImageView nevBack;
    TextView headerTitle;
    RecyclerView recyclerView;
    MyReviewAdapter adapter;
    List<MyReviewModel.ReviewData> list = new ArrayList<>();
    String variable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        variable = intent.getStringExtra("variable");

        init();
        recyclerData();
        getReview(Glob.Token,Glob.user_id,"25");
    }

    public void init() {
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Review");

        Glob.progressDialog(this);
        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent;
//                if (variable.equals("variable")){
//
//                    intent = new Intent(getApplicationContext(), MainActivity.class);
//                }
//                else {
//
//                    intent = new Intent(getApplicationContext(), DoctorProfile.class);
//                    intent.putExtra("doctorId",variable);
//
//                }
//                startActivity(intent);
                finish();
            }
        });
    }

    public void getReview(String token, String user_id, String doctor_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getReview(token, user_id, doctor_id).enqueue(new Callback<MyReviewModel>() {
            @Override
            public void onResponse(Call<MyReviewModel> call, Response<MyReviewModel> response) {

                MyReviewModel myReviewModel = response.body();

                List<MyReviewModel.ReviewData> dataList = myReviewModel.getReviewDataList();

                for (int i = 0; i < dataList.size(); i++) {

                    MyReviewModel.ReviewData model = dataList.get(i);

                    MyReviewModel.ReviewData data = new MyReviewModel.ReviewData(model.getUserDetail(), model.getMessage(), model.getRating(), model.getDate());

                    list.add(data);

                    Glob.dialog.dismiss();
                }
                recyclerData();

            }

            @Override
            public void onFailure(Call<MyReviewModel> call, Throwable t) {

            }
        });
    }

    public void recyclerData() {


        adapter = new MyReviewAdapter(list, this, new MyReviewAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}