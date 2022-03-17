package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.in.patient.R;
import com.in.patient.adapter.LabDetailTestAdapter;
import com.in.patient.adapter.TestAdapter;
import com.in.patient.adapter.TestPackageAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.AllTestModel;
import com.in.patient.model.LabDetailModel;
import com.in.patient.model.TestPackagesModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabDetail extends AppCompatActivity {

    ImageView backButton,labImage;
    RecyclerView labTestPackagesRecycler, labTestRecycler;
    TestPackageAdapter testPackageAdapter;
    List<TestPackagesModel.PackagesData> packagesDataList = new ArrayList<>();
    LabDetailTestAdapter testAdapter;
    List<AllTestModel.TestData> testDataList = new ArrayList<>();
    TextView viewAllPackages, labName, labEmail, labNumber, labCity, labLocation;
    Button bookLabAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_detail);
        getSupportActionBar().hide();
        init();
        getPackages(Glob.Token, Glob.user_id);
        getTest(Glob.Token, Glob.user_id,"60");
        getLabDetail(Glob.Token,Glob.user_id,"60");
    }

    public void init() {
        Glob.progressDialog(this);
        labTestPackagesRecycler = findViewById(R.id.labTestPackagesRecycler);
        backButton = findViewById(R.id.backButton);
        labTestRecycler = findViewById(R.id.labTestRecycler);
        viewAllPackages = findViewById(R.id.viewAllPackages);
        bookLabAppointment = findViewById(R.id.bookLabAppointment);

        labImage = findViewById(R.id.labImage);
        labName = findViewById(R.id.labName);
        labEmail = findViewById(R.id.labEmail);
        labNumber = findViewById(R.id.labNumber);
        labCity = findViewById(R.id.labCity);
        labLocation = findViewById(R.id.labLocation);


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
        bookLabAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LabTestBooking.class);
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

    public void getTest(String token, String user_id,String lab_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getLabTest(token, user_id,lab_id).enqueue(new Callback<AllTestModel>() {
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


    public void getLabDetail(String token, String user_id, String lab_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getLabDetail(token, user_id, lab_id).enqueue(new Callback<LabDetailModel>() {
            @Override
            public void onResponse(Call<LabDetailModel> call, Response<LabDetailModel> response) {

                LabDetailModel labDetailModel = response.body();

                LabDetailModel.LabDetailData model = labDetailModel.getData();


                Glide.with(getApplicationContext()).load(model.getLab_image()).into(labImage);
                labName.setText(model.getLab_name());
                labEmail.setText(model.getEmail());
                labNumber.setText(model.getMobile_no());
                labCity.setText(model.getCity());
                labLocation.setText(model.getAddress());


                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<LabDetailModel> call, Throwable t) {

            }
        });
    }
}