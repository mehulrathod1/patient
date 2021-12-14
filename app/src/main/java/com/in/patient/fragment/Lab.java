package com.in.patient.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.patient.R;
import com.in.patient.activity.MainActivity;
import com.in.patient.adapter.LabAdapter;
import com.in.patient.adapter.LabTestAdapter;
import com.in.patient.model.LabModel;
import com.in.patient.model.LabTestModel;

import java.util.ArrayList;
import java.util.List;


public class Lab extends Fragment {

    LabAdapter adapter;
    RecyclerView recyclerView;
    List<LabModel> list = new ArrayList<>();
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lab, container, false);
        init();
        recyclerData();
        return view;
    }

    public void init() {


        recyclerView = view.findViewById(R.id.recycler);


    }

    public void recyclerData() {

        LabModel model = new LabModel("LAB name", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et.",
                "Location");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new LabAdapter(list, getContext(), new LabAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}