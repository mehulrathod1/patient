package com.in.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.ProfileMedicalModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingMedical extends Fragment {

    View view;
    EditText edtDetail, edtCurrent, edtPast, edtAny;
    Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_setting_medical, container, false);
        init();
        getProfileMedical(Glob.Token, Glob.user_id);
        return view;
    }

    public void init() {
        edtDetail = view.findViewById(R.id.edtDetail);
        edtCurrent = view.findViewById(R.id.edtCurrent);
        edtPast = view.findViewById(R.id.edtPast);
        edtAny = view.findViewById(R.id.edtAny);
        btnSubmit = view.findViewById(R.id.btnSubmit);


    }

    public void getProfileMedical(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);


        call.getProfileMedical(token, user_id).enqueue(new Callback<ProfileMedicalModel>() {
            @Override
            public void onResponse(Call<ProfileMedicalModel> call, Response<ProfileMedicalModel> response) {
                ProfileMedicalModel model = response.body();


                edtDetail.setText(model.getData().getDetails_of_allergies());
                edtCurrent.setText(model.getData().getCurrent_and_past_medication());
                edtPast.setText(model.getData().getPast_surgery_injury());
                edtAny.setText(model.getData().getChronic_disease());

            }

            @Override
            public void onFailure(Call<ProfileMedicalModel> call, Throwable t) {

            }
        });
    }
}