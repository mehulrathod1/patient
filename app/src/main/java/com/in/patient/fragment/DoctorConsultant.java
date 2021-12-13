package com.in.patient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.activity.Search;
import com.in.patient.adapter.DoctorConsultantAdapter;
import com.in.patient.model.DoctorConsultModel;

import java.util.ArrayList;
import java.util.List;

public class DoctorConsultant extends Fragment {
    RecyclerView recyclerView;
    DoctorConsultantAdapter adapter;
    List<DoctorConsultModel> list = new ArrayList<>();
    View view;
    LinearLayout searchLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctor_consultant, container, false);

        init();
        recyclerData();

        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);
        searchLayout = view.findViewById(R.id.searchLayout);


        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search.class);
                startActivity(intent);
            }
        });
    }

    public void recyclerData() {

        DoctorConsultModel model = new DoctorConsultModel("Dr. Daksh Kumar", "Hair Transplat Surgeon,", "17 yrs of exp. overall", "Location", "95%", "125");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new DoctorConsultantAdapter(list, getContext(), new DoctorConsultantAdapter.Click() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), DoctorProfile.class);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}