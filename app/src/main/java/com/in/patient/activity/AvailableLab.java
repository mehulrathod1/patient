package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class AvailableLab extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;

    RecyclerView labRecycle, testNameRecycler;
    LabAdapter adapter;
    PackageTestAdapter packageTestAdapter;
    List<PackageTestModel> packageTestModelList = new ArrayList<>();
    List<LabModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_lab);
        getSupportActionBar().hide();
        init();
        labData();
        testName();
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

    public void labData() {

        LabModel model = new LabModel("LAB name", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et.",
                "Location");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new LabAdapter(list, getApplicationContext(), new LabAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        labRecycle.setLayoutManager(mLayoutManager);
        labRecycle.setNestedScrollingEnabled(false);
        labRecycle.setAdapter(adapter);
    }

}