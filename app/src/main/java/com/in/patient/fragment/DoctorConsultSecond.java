package com.in.patient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.adapter.DoctorConsultantSecondAdapter;
import com.in.patient.model.DoctorConsultantSecondModel;

import java.util.ArrayList;
import java.util.List;

public class DoctorConsultSecond extends Fragment {

    RecyclerView recyclerView;
    DoctorConsultantSecondAdapter adapter;
    List<DoctorConsultantSecondModel> list = new ArrayList<>();
    View view;
    FloatingActionButton filter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctor_consult_second, container, false);

        init();
        recyclerData();
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);
        filter = view.findViewById(R.id.Filter);

        final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.filter_dialog);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });
    }

    public void recyclerData() {

        DoctorConsultantSecondModel model = new DoctorConsultantSecondModel("Dr. Daksh Kumar", "Hair Transplat Surgeon,", "17 yrs of exp. overall", "Location", "Available", "95%", "125");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new DoctorConsultantSecondAdapter(list, getContext(), new DoctorConsultantSecondAdapter.Click() {
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