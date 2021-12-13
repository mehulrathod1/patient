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
import com.in.patient.activity.DoctorProfile;
import com.in.patient.activity.Search;
import com.in.patient.adapter.DoctorConsultantAdapter;
import com.in.patient.adapter.MedicinesAdapter;
import com.in.patient.model.DoctorConsultModel;
import com.in.patient.model.MedicinesModel;

import java.util.ArrayList;
import java.util.List;

public class Medicines extends Fragment {

    RecyclerView recyclerView;
    MedicinesAdapter adapter;
    List<MedicinesModel> list = new ArrayList<>();
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_medicines, container, false);
        init();
        recyclerData();
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);

    }

    public void recyclerData() {

        MedicinesModel model = new MedicinesModel("Medicines name", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et.", "$199");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new MedicinesAdapter(list, getContext(), new MedicinesAdapter.Click() {
            @Override
            public void OnItemClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}