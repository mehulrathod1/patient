package com.in.patient.fragment;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

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

import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.activity.Search;
import com.in.patient.adapter.DoctorConsultantAdapter;
import com.in.patient.adapter.MedicinesAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.DoctorConsultModel;
import com.in.patient.model.MedicinesModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medicines extends Fragment {

    RecyclerView recyclerView;
    MedicinesAdapter adapter;
    List<MedicinesModel.MedicinesData> list = new ArrayList<>();
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
        getMedicines(Token, user_id);
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);

        Glob.progressDialog(getContext());
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void getMedicines(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getMedicines(token, user_id).enqueue(new Callback<MedicinesModel>() {
            @Override
            public void onResponse(Call<MedicinesModel> call, Response<MedicinesModel> response) {

                list.clear();
                MedicinesModel medicinesModel = response.body();

                List<MedicinesModel.MedicinesData> DataList = medicinesModel.getMedicinesDataList();

                for (int i = 0; i < DataList.size(); i++) {

                    MedicinesModel.MedicinesData model = DataList.get(i);

                    MedicinesModel.MedicinesData data = new MedicinesModel.MedicinesData(
                            model.getMedicines_name(), "$" + model.getPrice(), model.getDescription(),
                            model.getMedicine_image()
                    );
                    list.add(data);
                }
                recyclerData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MedicinesModel> call, Throwable t) {

            }
        });

    }

    public void recyclerData() {


        adapter = new MedicinesAdapter(list, getContext(), new MedicinesAdapter.Click() {
            @Override
            public void OnItemClick(int position) {

                Fragment fragment = new Cart();
                loadFragment(fragment);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}