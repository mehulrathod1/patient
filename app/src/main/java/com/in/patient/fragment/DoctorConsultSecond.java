package com.in.patient.fragment;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.in.patient.globle.Glob;
import com.in.patient.model.DoctorConsultantSecondModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorConsultSecond extends Fragment {

    RecyclerView recyclerView;
    DoctorConsultantSecondAdapter adapter;
    List<DoctorConsultantSecondModel.ConsultantData> list = new ArrayList<>();
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
        getDoctor(Token, user_id);
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);
        filter = view.findViewById(R.id.Filter);

        final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.filter_dialog);

        Glob.progressDialog(getContext());


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });
    }

    public void getDoctor(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getDoctor(token, user_id).enqueue(new Callback<DoctorConsultantSecondModel>() {
            @Override
            public void onResponse(Call<DoctorConsultantSecondModel> call, Response<DoctorConsultantSecondModel> response) {

                DoctorConsultantSecondModel doctorConsultantSecondModel = response.body();

                List<DoctorConsultantSecondModel.ConsultantData> DataList = doctorConsultantSecondModel.getConsultantDataList();

                for (int i = 0; i < DataList.size(); i++) {

                    DoctorConsultantSecondModel.ConsultantData model = DataList.get(i);

                    DoctorConsultantSecondModel.ConsultantData data = new DoctorConsultantSecondModel.ConsultantData(
                            model.getUser_id(), model.getFirst_name(), model.getLast_name(),
                            model.getSpecialist(), model.getExperience() + " yrs of exp overall", model.getLocation(),
                            model.getProfile_image()
                    );

                    list.add(data);
                }
                recyclerData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<DoctorConsultantSecondModel> call, Throwable t) {

            }
        });

    }

    public void recyclerData() {

        adapter = new DoctorConsultantSecondAdapter(list, getContext(), new DoctorConsultantSecondAdapter.Click() {
            @Override
            public void onItemClick(int position) {


                String doctorId = list.get(position).getUser_id();

                Intent intent = new Intent(getContext(), DoctorProfile.class);
                intent.putExtra("doctorId", doctorId);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }


}