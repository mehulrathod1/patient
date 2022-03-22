package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.TestPackageAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.TestPackagesModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabPackages extends AppCompatActivity {

        ImageView nevBack;
        TextView headerTitle;

    RecyclerView labTestPackagesRecycler;
    TestPackageAdapter testPackageAdapter;
    List<TestPackagesModel.PackagesData> packagesDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_packages);
        getSupportActionBar().hide();

        init();
        getPackages(Glob.Token,Glob.user_id);
    }

    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        labTestPackagesRecycler = findViewById(R.id.labTestPackagesRecycler);
        headerTitle.setText("All Packages");
        Glob.progressDialog(this);

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                            model.getPackage_id(), model.getPackage_name());

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

                Intent intent = new Intent(getApplicationContext(),AvailableLab.class);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        labTestPackagesRecycler.setLayoutManager(mLayoutManager);
        labTestPackagesRecycler.setAdapter(testPackageAdapter);

    }

}