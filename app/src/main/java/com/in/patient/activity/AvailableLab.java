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
import com.in.patient.adapter.LabAdapter;
import com.in.patient.adapter.PackageTestAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.LabModel;
import com.in.patient.model.PackageTestModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableLab extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    RecyclerView labRecycle, testNameRecycler;
    LabAdapter adapter;
    PackageTestAdapter packageTestAdapter;
    List<PackageTestModel> packageTestModelList = new ArrayList<>();
    List<LabModel.LabListData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_lab);
        getSupportActionBar().hide();
        init();
        labData();
        testName();
        getAllLab(Glob.Token,Glob.user_id,Glob.yourLocation);
    }

    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        headerTitle.setText("Available Labs");
        labRecycle = findViewById(R.id.labRecycle);
        testNameRecycler = findViewById(R.id.testNameRecycler);

        Glob.progressDialog(this);

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void testName() {
        PackageTestModel model = new PackageTestModel("Test 1");
        PackageTestModel model1 = new PackageTestModel("Test 1Test 1Test 1Test 1Test 1");
        packageTestModelList.add(model1);
        packageTestModelList.add(model);
        packageTestModelList.add(model);
        packageTestModelList.add(model);
        packageTestModelList.add(model);
        packageTestModelList.add(model);
        packageTestModelList.add(model);
        packageTestModelList.add(model);


        packageTestAdapter = new PackageTestAdapter(packageTestModelList, getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        testNameRecycler.setLayoutManager(mLayoutManager);

        testNameRecycler.setAdapter(packageTestAdapter);
    }
    public void getAllLab(String token, String user_id, String city) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getAllLab(token, user_id, city).enqueue(new Callback<LabModel>() {
            @Override
            public void onResponse(Call<LabModel> call, Response<LabModel> response) {

                LabModel labModel = response.body();

                List<LabModel.LabListData> dataList = labModel.getLabListData();

                for (int i = 0; i < dataList.size(); i++) {

                    LabModel.LabListData model = dataList.get(i);

                    LabModel.LabListData data = new LabModel.LabListData(
                            model.getLab_id(), model.getLab_name(), model.getLocation(), model.getImage()
                    );
                    list.add(data);


                }
                labData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<LabModel> call, Throwable t) {

            }
        });
    }

    public void labData() {


        adapter = new LabAdapter(list, getApplicationContext(), new LabAdapter.Click() {
            @Override
            public void onItemClick(int position) {


                Intent intent = new Intent(getApplicationContext(), LabDetail.class);
                startActivity(intent);

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        labRecycle.setLayoutManager(mLayoutManager);
        labRecycle.setAdapter(adapter);
    }


}