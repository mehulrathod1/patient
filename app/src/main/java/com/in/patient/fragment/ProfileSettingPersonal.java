package com.in.patient.fragment;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.ProfilePersonalModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingPersonal extends Fragment {

    View view;
    EditText edtFirstName, edtLastName, edtEmail, edtNumber, edtAge, edtGender, edtDob, edtBloodGroup, edtMaritalStatus, edtHeight, edtWeight, edtEmergencyNumber, edtAddress;

    TextView choosePhoto;
    Button Submit;
    ImageView profileImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_setting_personal, container, false);

        init();

        getProfilePersonal(Token, user_id);
        return view;
    }

    private void init() {

        Glob.progressDialog(getContext());
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtNumber = view.findViewById(R.id.edtNumber);
        edtAge = view.findViewById(R.id.edtAge);
        edtGender = view.findViewById(R.id.edtGender);
        edtDob = view.findViewById(R.id.edtDob);
        edtBloodGroup = view.findViewById(R.id.edtBloodGroup);
        edtMaritalStatus = view.findViewById(R.id.edtMaritalStatus);
        edtHeight = view.findViewById(R.id.edtHeight);
        edtWeight = view.findViewById(R.id.edtWeight);
        edtEmergencyNumber = view.findViewById(R.id.edtEmergencyNumber);
        edtAddress = view.findViewById(R.id.edtAddress);


    }


    public void getProfilePersonal(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getProfilePersonal(token, user_id).enqueue(new Callback<ProfilePersonalModel>() {
            @Override
            public void onResponse(Call<ProfilePersonalModel> call, Response<ProfilePersonalModel> response) {
                ProfilePersonalModel model = response.body();



                edtFirstName.setText(model.getData().getFirst_name());
                edtLastName.setText(model.getData().getLast_name());
                edtEmail.setText(model.getData().getEmail());
                edtNumber.setText(model.getData().getMobile_number());
                edtAge.setText(model.getData().getAge());
                edtGender.setText(model.getData().getGender());
                edtDob.setText(model.getData().getDOB());
                edtBloodGroup.setText(model.getData().getBlood_group());
                edtMaritalStatus.setText(model.getData().getMarital_status());
                edtHeight.setText(model.getData().getHeight());
                edtWeight.setText(model.getData().getWeight());
                edtEmergencyNumber.setText(model.getData().getEmergency_contact());
                edtAddress.setText(model.getData().getAddress());




                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProfilePersonalModel> call, Throwable t) {

            }
        });

    }
}