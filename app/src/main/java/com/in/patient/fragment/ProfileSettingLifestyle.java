package com.in.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.ProfileLifestyleModel;
import com.in.patient.model.ProfileMedicalModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingLifestyle extends Fragment {

    View view;
    Spinner spnSmoking, spnAlcohol, spnWorkoutLevel;
    EditText edtSportInvolvement;
    Button btnSubmit;
    ArrayAdapter<String> smokingAdapter, alcoholAdapter, workoutAdapter;
    List<String> smokingList, alcoholList, workoutList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_setting_lifestyle, container, false);
        init();
        getProfileLifestyle(Glob.Token, Glob.user_id);

        return view;
    }

    public void init() {

        spnSmoking = view.findViewById(R.id.spnSmoking);
        spnAlcohol = view.findViewById(R.id.spnAlcohol);
        spnWorkoutLevel = view.findViewById(R.id.spnWorkoutLevel);
        edtSportInvolvement = view.findViewById(R.id.edtSportInvolvement);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        smokingList = new ArrayList<>();
        smokingList.add("yes");
        smokingList.add("No");


        smokingAdapter = new ArrayAdapter<String>(getContext(), R.layout.profile_spinner_text, smokingList);
        smokingAdapter.setDropDownViewResource(R.layout.dropdown_item);
        spnSmoking.setAdapter(smokingAdapter);

        alcoholList = new ArrayList<>();
        alcoholList.add("yes");
        alcoholList.add("No");


        alcoholAdapter = new ArrayAdapter<String>(getContext(), R.layout.profile_spinner_text, alcoholList);
        alcoholAdapter.setDropDownViewResource(R.layout.dropdown_item);
        spnAlcohol.setAdapter(alcoholAdapter);


        workoutList = new ArrayList<>();
        workoutList.add("High");
        workoutList.add("Medium");


        workoutAdapter = new ArrayAdapter<String>(getContext(), R.layout.profile_spinner_text, workoutList);
        workoutAdapter.setDropDownViewResource(R.layout.dropdown_item);
        spnWorkoutLevel.setAdapter(workoutAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfileLifestyle(Glob.Token, Glob.user_id, spnSmoking.getSelectedItem().toString(),
                        spnAlcohol.getSelectedItem().toString(),
                        spnWorkoutLevel.getSelectedItem().toString(), edtSportInvolvement.getText().toString()
                );

            }
        });

    }


    public void getProfileLifestyle(String token, String user_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);


        call.getProfileLifestyle(token, user_id).enqueue(new Callback<ProfileLifestyleModel>() {
            @Override
            public void onResponse(Call<ProfileLifestyleModel> call, Response<ProfileLifestyleModel> response) {

                ProfileLifestyleModel model = response.body();


                Log.e("vataf", "onResponse: " + model.getData().getAlcohol());
                Log.e("vataf", "onResponse: " + model.getData().getSports_involvement());

                edtSportInvolvement.setText(model.getData().getSports_involvement());

                if (model.getData().getSmoking().equals("no")) {
                    spnSmoking.setSelection(1);
                } else {
                    spnSmoking.setSelection(0);
                }
                if (model.getData().getAlcohol().equals("no")) {
                    spnAlcohol.setSelection(1);
                } else {
                    spnAlcohol.setSelection(0);
                }

                if (model.getData().getWorkout_level().equals("high")) {
                    spnWorkoutLevel.setSelection(0);
                } else {
                    spnWorkoutLevel.setSelection(1);
                }
            }

            @Override
            public void onFailure(Call<ProfileLifestyleModel> call, Throwable t) {

            }
        });

    }


    public void updateProfileLifestyle(String token,
                                       String user_id,
                                       String smoking,
                                       String alchol,
                                       String workout_level,
                                       String sports_involvement) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.updateProfileLifestyle(token, user_id, smoking, alchol, workout_level, sports_involvement).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                getProfileLifestyle(Glob.Token, Glob.user_id);


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }
}