package com.in.patient.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.in.patient.R;
import com.in.patient.adapter.HealthCareAdapter;
import com.in.patient.model.CareAndCheckupModel;

import java.util.ArrayList;
import java.util.List;

public class CareServices extends Fragment {
    RecyclerView healthCareRecycler;
    HealthCareAdapter healthCareAdapter;
    List<CareAndCheckupModel> careList = new ArrayList<>();
    View view;
    TextView bookNow;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    ImageView dialogClose;

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
        healthCareRecycler = view.findViewById(R.id.healthCareRecycler);
        bookNow = view.findViewById(R.id.bookNow);

        alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.book_now_dialog, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();

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
}