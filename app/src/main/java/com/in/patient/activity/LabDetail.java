package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.LabDetailTestAdapter;
import com.in.patient.adapter.TestAdapter;
import com.in.patient.adapter.TestPackageAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.AllTestModel;
import com.in.patient.model.TestPackagesModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabDetail extends AppCompatActivity {

    ImageView backButton;
    RecyclerView labTestPackagesRecycler, labTestRecycler;
    TestPackageAdapter testPackageAdapter;
    List<TestPackagesModel.PackagesData> packagesDataList = new ArrayList<>();
    LabDetailTestAdapter testAdapter;
    List<AllTestModel.TestData> testDataList = new ArrayList<>();
    TextView viewAllPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_detail);
        getSupportActionBar().hide();
        init();
        getPackages(Glob.Token, Glob.user_id);
        getTest(Glob.Token, Glob.user_id);
    }

    public void init() {
        Glob.progressDialog(this);
        labTestPackagesRecycler = findViewById(R.id.labTestPackagesRecycler);
        backButton = findViewById(R.id.backButton);
        labTestRecycler = findViewById(R.id.labTestRecycler);
        viewAllPackages = findViewById(R.id.viewAllPackages);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewAllPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LabPackages.class);
                startActivity(intent);
            }
        });
    }

    public void getPackages(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getPackages(token, user_id).enqueue(new Callback<TestPackagesModel>() {
            @Override
            public void onResponse(Call<TestPackagesModel> call, Response<TestPackagesModel> response) {


                TestPackagesModel testPackagesModel = response.body();
                List<TestPackagesModel.PackagesData> dataList = testPackagesModel.getPackagesData();

                for (int i = 0; i < dataList.size(); i++) {

                    TestPackagesModel.PackagesData model = dataList.get(i);

                    TestPackagesModel.PackagesData data = new TestPackagesModel.PackagesData(
                            model.getPackage_id(), model.getPackage_name(), model.getPrice());

                    packagesDataList.add(data);


                }
                labPackageData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<TestPackagesModel> call, Throwable t) {

            }
        });
    }

    public void labPackageData() {


        testPackageAdapter = new TestPackageAdapter(packagesDataList, getApplicationContext(), new TestPackageAdapter.Click() {
            @Override
            public void onBookClick(int position) {

                Intent intent = new Intent(getApplicationContext(), AvailableLab.class);
                startActivity(intent);

            }
        });

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        labTestPackagesRecycler.setLayoutManager(mLayoutManager);
        labTestPackagesRecycler.setAdapter(testPackageAdapter);

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


        testAdapter = new LabDetailTestAdapter(testDataList, getApplicationContext());

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        labTestRecycler.setLayoutManager(mLayoutManager);
        labTestRecycler.setNestedScrollingEnabled(false);
        labTestRecycler.setAdapter(testAdapter);

    }


}