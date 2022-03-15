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
import com.in.patient.adapter.LabAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.LabModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllLabActivity extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    RecyclerView labRecycle;
    LabAdapter adapter;
    List<LabModel.LabListData> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lab);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String testId = intent.getStringExtra("testId");

        Log.e("testId", "onCreate: "+testId);


        init();
        labData();
        getAllLab(Glob.Token, Glob.user_id, Glob.yourLocation);
    }

    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        headerTitle.setText("All Labs");
        labRecycle = findViewById(R.id.labRecycle);
        Glob.progressDialog(this);

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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