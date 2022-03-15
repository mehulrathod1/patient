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
import com.in.patient.activity.ViewBookingDetail;
import com.in.patient.adapter.MyAppointmentAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.MyAppointmentModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompletedAppointment extends Fragment {

    View view;

    MyAppointmentAdapter adapter;
    RecyclerView recyclerView;
    List<MyAppointmentModel.AppointmentData> list = new ArrayList<>();

    String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_completed_appointment, container, false);
        init();
        getMyAppointment(Glob.Token,Glob.user_id);
        return view;

    }

    public void init(){
        recyclerView = view.findViewById(R.id.recycler);

    }

    public void getMyAppointment(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.getMyAppointment(token, user_id).enqueue(new Callback<MyAppointmentModel>() {
            @Override
            public void onResponse(Call<MyAppointmentModel> call, Response<MyAppointmentModel> response) {

                MyAppointmentModel myAppointmentModel = response.body();
                List<MyAppointmentModel.AppointmentData> DataList = myAppointmentModel.getMedicinesDataList();

                for (int i = 0; i < DataList.size(); i++) {
                    MyAppointmentModel.AppointmentData model = DataList.get(i);

                    MyAppointmentModel.AppointmentData data = new MyAppointmentModel.AppointmentData(
                            model.getBooingId(),
                            model.getDoctorName(),
                            model.getLocation(),
                            model.getStatus(),
                            model.getProfile(),
                            model.getFees(),
                            model.getFees()
                    );

                    list.add(data);
                }
                recyclerData();

            }

            @Override
            public void onFailure(Call<MyAppointmentModel> call, Throwable t) {

            }
        });

    }

    public void recyclerData() {


        adapter = new MyAppointmentAdapter(list, getContext(), new MyAppointmentAdapter.Click() {
            @Override
            public void onButtonClick(int position) {

                id = list.get(position).getBooingId();
                Intent intent = new Intent(getContext(), ViewBookingDetail.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}