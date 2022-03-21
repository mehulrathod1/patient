package com.in.patient.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.in.patient.R;
import com.in.patient.adapter.HealthCareAdapter;
import com.in.patient.adapter.HomeCareAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CareAndCheckupModel;
import com.in.patient.model.CommonModel;
import com.in.patient.model.HomeCareModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CareServices extends Fragment {
    RecyclerView healthCareRecycler;
     View view;
    TextView bookNow, CareSubmit,viewAllCare;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    ImageView dialogClose;
    EditText Care, CareName, CareEmail, CareNumber, CareAddress;

    List<HomeCareModel.CareData> careDataList = new ArrayList<>();
    HomeCareAdapter homeCareAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_care_services, container, false);
        init();
        getHomeCareServices(Glob.Token,Glob.user_id);
        return view;
    }

    public void init() {

        Glob.progressDialog(getContext());
        healthCareRecycler = view.findViewById(R.id.healthCareRecycler);
        bookNow = view.findViewById(R.id.bookNow);
        viewAllCare = view.findViewById(R.id.viewAllCare);

        viewAllCare.setText(Html.fromHtml("<u>View All</u>"));


        alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.book_now_dialog, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();
        Care = dialogLayout.findViewById(R.id.Care);
        CareName = dialogLayout.findViewById(R.id.CareName);
        CareEmail = dialogLayout.findViewById(R.id.CareEmail);
        CareNumber = dialogLayout.findViewById(R.id.careNumber);
        CareAddress = dialogLayout.findViewById(R.id.CareAddress);
        CareSubmit = dialogLayout.findViewById(R.id.CareSubmit);
        dialogClose = dialogLayout.findViewById(R.id.dialogClose);

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        CareSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String a = Care.getText().toString() + CareName.getText().toString() + CareEmail.getText().toString() +
                        CareNumber.getText().toString() + CareAddress.getText().toString();


                if (Care.getText().toString().equals("")) {
                    Care.setError("Please Enter FirstName");
                } else if (CareName.getText().toString().equals("")) {
                    CareName.setError("Please Enter Name");
                } else if (CareEmail.getText().toString().equals("")) {
                    CareEmail.setError("Please Enter Email");
                } else if (CareNumber.getText().toString().equals("")) {
                    CareNumber.setError("Please Enter Mobile Number");
                } else if (CareAddress.getText().toString().equals("")) {
                    CareAddress.setError("Please Enter Address");
                } else {
                    SubmitCare(Glob.Token, Glob.user_id, CareName.getText().toString(),
                            CareEmail.getText().toString(), CareNumber.getText().toString(),
                            Care.getText().toString(), CareAddress.getText().toString());
                }
            }
        });

        viewAllCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new HomeCareCategoryFragment();
                loadFragment(fragment);

            }
        });
    }



    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void SubmitCare(String token, String userId, String name, String email, String number, String care, String address) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.addCare(token, userId, name, email, number, care, address).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
                alert.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
            }
        });
    }

    public void getHomeCareServices(String token, String userId){


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getHomeCareServices(token,userId).enqueue(new Callback<HomeCareModel>() {
            @Override
            public void onResponse(Call<HomeCareModel> call, Response<HomeCareModel> response) {

                careDataList.clear();
                HomeCareModel homeCareModel = response.body();
                List<HomeCareModel.CareData> dataList = homeCareModel.getCareDataList();
                for (int i = 0;i<dataList.size();i++){

                    HomeCareModel.CareData model = dataList.get(i);

                    HomeCareModel.CareData data = new HomeCareModel.CareData(
                            model.getService_id(),model.getServiceName(),model.getImage());


                    careDataList.add(data);
                }
                homeCareData();
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<HomeCareModel> call, Throwable t) {
                Glob.dialog.dismiss();
            }
        });
    }


    public void homeCareData() {


        homeCareAdapter = new HomeCareAdapter(careDataList, getContext(), new HomeCareAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        healthCareRecycler.setLayoutManager(mLayoutManager);
        healthCareRecycler.setAdapter(homeCareAdapter);
    }

}