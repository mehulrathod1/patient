package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.TestAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.AllTestModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTestActivity extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    RecyclerView labTestRecycler;
    List<AllTestModel.TestData> testDataList = new ArrayList<>();
    TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_test);
        getSupportActionBar().hide();

        init();
        getTest(Glob.Token, Glob.user_id);
    }


    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        labTestRecycler = findViewById(R.id.labTestRecycler);
        headerTitle.setText("All Test");
        Glob.progressDialog(this);

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getTest(String token, String user_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getTest(token, user_id).enqueue(new Callback<AllTestModel>() {
            @Override
            public void onResponse(Call<AllTestModel> call, Response<AllTestModel> response) {

                AllTestModel allTestModel = response.body();

                List<AllTestModel.TestData> dataList = allTestModel.getLabData();
                for (int i = 0; i < dataList.size(); i++) {


                    AllTestModel.TestData model = dataList.get(i);

                    AllTestModel.TestData data = new AllTestModel.TestData(model.getId(),
                            model.getTest_name(), model.getTest_description());

                    testDataList.add(data);

                    Log.e("no", "onResponse: " + model.getId());
                }
                labTestData();
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<AllTestModel> call, Throwable t) {

            }
        });
    }

    public void labTestData() {


        testAdapter = new TestAdapter(testDataList, getApplicationContext(), new TestAdapter.Click() {
            @Override
            public void onBookClick(int position) {

                Intent intent = new Intent(getApplicationContext(), AllLabActivity.class);
                startActivity(intent);

            }
        });

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        labTestRecycler.setLayoutManager(mLayoutManager);
        labTestRecycler.setNestedScrollingEnabled(false);
        labTestRecycler.setAdapter(testAdapter);

    }

}