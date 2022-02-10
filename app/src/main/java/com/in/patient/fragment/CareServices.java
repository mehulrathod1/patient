package com.in.patient.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
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
import com.in.patient.globle.Glob;
import com.in.patient.model.CareAndCheckupModel;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CareServices extends Fragment {
    RecyclerView healthCareRecycler;
    HealthCareAdapter healthCareAdapter;
    List<CareAndCheckupModel> careList = new ArrayList<>();
    View view;
    TextView bookNow, CareSubmit;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    ImageView dialogClose;
    EditText Care, CareName, CareEmail, CareNumber, CareAddress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_care_services, container, false);
        init();
        healthCareData();
        return view;
    }

    public void init() {

        Glob.progressDialog(getContext());
        healthCareRecycler = view.findViewById(R.id.healthCareRecycler);
        bookNow = view.findViewById(R.id.bookNow);

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
    }

    public void healthCareData() {

        CareAndCheckupModel model = new CareAndCheckupModel("", "Covid Care");
        CareAndCheckupModel model1 = new CareAndCheckupModel("", "Physiotherapy");
        CareAndCheckupModel model2 = new CareAndCheckupModel("", "Medical Equipment");
        CareAndCheckupModel model3 = new CareAndCheckupModel("", "Gynaecologist");
        careList.add(model);
        careList.add(model1);
        careList.add(model2);
        careList.add(model3);
        careList.add(model);
        careList.add(model1);
        careList.add(model2);
        careList.add(model3);

        healthCareAdapter = new HealthCareAdapter(careList, getContext(), new HealthCareAdapter.Click() {
            @Override
            public void onClick(int position) {

                Fragment fragment = new ServiceCategory();
                loadFragment(fragment);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        healthCareRecycler.setLayoutManager(mLayoutManager);
        healthCareRecycler.setAdapter(healthCareAdapter);
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

}