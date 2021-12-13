package com.in.patient.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.activity.Search;
import com.in.patient.adapter.DoctorConsultantAdapter;
import com.in.patient.adapter.ServiceCategoryAdapter;
import com.in.patient.model.DoctorConsultModel;
import com.in.patient.model.ServiceCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceCategory extends Fragment {

    RecyclerView recyclerView;
    ServiceCategoryAdapter adapter;
    List<ServiceCategoryModel> list = new ArrayList<>();
    View view;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    ImageView dialogClose;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_searvice_category, container, false);
        init();
        recyclerData();
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);

        alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.book_now_dialog, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();

        dialogClose = dialogLayout.findViewById(R.id.dialogClose);

        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }

    public void recyclerData() {

        ServiceCategoryModel model = new ServiceCategoryModel("Service category", "$199", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);

        adapter = new ServiceCategoryAdapter(list, getContext(), new ServiceCategoryAdapter.Click() {
            @Override
            public void onItemClick(int position) {
                alert.show();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}