package com.in.patient.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.activity.AllLabActivity;
import com.in.patient.activity.AllTestActivity;
import com.in.patient.activity.AvailableLab;
import com.in.patient.activity.BookingWithCall;
import com.in.patient.activity.LabDetail;
import com.in.patient.activity.LabPackages;
import com.in.patient.activity.MainActivity;
import com.in.patient.activity.PrescriptionActivity;
import com.in.patient.adapter.LabAdapter;
import com.in.patient.adapter.LabTestAdapter;
import com.in.patient.adapter.TestAdapter;
import com.in.patient.adapter.TestPackageAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.AllTestModel;
import com.in.patient.model.LabModel;
import com.in.patient.model.LabTestModel;
import com.in.patient.model.TestPackagesModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Lab extends Fragment {

    LabAdapter adapter;
    List<LabModel.LabListData> list = new ArrayList<>();
    View view;
    TextView viewAllPackages, viewAllTest, viewAllLab;
    LinearLayout prescriptionLayout, callLayout;
    RecyclerView labTestPackagesRecycler, labTestRecycler, labRecycle;
    TestPackageAdapter testPackageAdapter;
    TestAdapter testAdapter;
    List<TestPackagesModel.PackagesData> packagesDataList = new ArrayList<>();
    List<AllTestModel.TestData> testDataList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lab, container, false);
        init();
        getPackages(Glob.Token, Glob.user_id);
        getTest(Glob.Token, Glob.user_id);
        getAllLab(Glob.Token, Glob.user_id, Glob.yourLocation);
        labData();
        return view;
    }

    public void init() {

        Glob.progressDialog(getContext());

        labTestPackagesRecycler = view.findViewById(R.id.labTestPackagesRecycler);
        labTestRecycler = view.findViewById(R.id.labTestRecycler);
        labRecycle = view.findViewById(R.id.labRecycle);
        viewAllPackages = view.findViewById(R.id.viewAllPackages);
        viewAllTest = view.findViewById(R.id.viewAllTest);
        viewAllLab = view.findViewById(R.id.viewAllLab);
        prescriptionLayout = view.findViewById(R.id.prescriptionLayout);
        callLayout = view.findViewById(R.id.callLayout);

        prescriptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrescriptionActivity.class);
                startActivity(intent);
            }
        });

        callLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), BookingWithCall.class);
                startActivity(intent);
            }
        });

        viewAllPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), LabPackages.class);
                startActivity(intent);
            }
        });
        viewAllTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AllTestActivity.class);
                startActivity(intent);
            }
        });
        viewAllLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AllLabActivity.class);
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
                            model.getPackage_id(), model.getPackage_name());

                    packagesDataList.add(data);

                    List<TestPackagesModel.PackagesData.PackageTest> packageTestList = model.getPackageTestList();

                    for (int j = 0 ; j<packageTestList.size();j++){

                        TestPackagesModel.PackagesData.PackageTest test = packageTestList.get(j);
                        TestPackagesModel.PackagesData.PackageTest packageTest = new TestPackagesModel.PackagesData.PackageTest(test.getTest(),test.getTest_name());

                        Log.e("packageTest", "onResponse: "+packageTest.getTest_name() );
                    }


                }
                labPackageData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<TestPackagesModel> call, Throwable t) {

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

    public void labPackageData() {


        testPackageAdapter = new TestPackageAdapter(packagesDataList, getContext(), new TestPackageAdapter.Click() {
            @Override
            public void onBookClick(int position) {

                Intent intent = new Intent(getContext(), AvailableLab.class);
                startActivity(intent);

            }
        });

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        labTestPackagesRecycler.setLayoutManager(mLayoutManager);
        labTestPackagesRecycler.setAdapter(testPackageAdapter);

    }

    public void labTestData() {


        testAdapter = new TestAdapter(testDataList, getContext(), new TestAdapter.Click() {
            @Override
            public void onBookClick(int position) {


                String testId = testDataList.get(position).getId();
                Intent intent = new Intent(getContext(), AllLabActivity.class);
                intent.putExtra("testId", testId);
                startActivity(intent);

            }
        });

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        labTestRecycler.setLayoutManager(mLayoutManager);
        labTestRecycler.setNestedScrollingEnabled(false);
        labTestRecycler.setAdapter(testAdapter);

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


        adapter = new LabAdapter(list, getContext(), new LabAdapter.Click() {
            @Override
            public void onItemClick(int position) {


                Intent intent = new Intent(getActivity(), LabDetail.class);
                startActivity(intent);

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        labRecycle.setLayoutManager(mLayoutManager);
        labRecycle.setAdapter(adapter);
    }

}