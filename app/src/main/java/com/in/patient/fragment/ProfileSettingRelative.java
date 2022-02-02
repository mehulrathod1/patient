package com.in.patient.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.LocusId;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.heartbeatinfo.SdkHeartBeatResult;
import com.in.patient.R;
import com.in.patient.activity.ViewBookingDetail;
import com.in.patient.adapter.MyAppointmentAdapter;
import com.in.patient.adapter.RelativeAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.RelativeModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingRelative extends Fragment {
    View view;

    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    ImageView dialogClose;
    Button Add_relative;
    TextView txt_add;
    EditText edt_name, edt_age, edt_gender, edt_blood_group, edt_marital_status;

    RecyclerView relative_recycler;
    RelativeAdapter relativeAdapter;
    List<RelativeModel.RelativeData> relativeDataList = new ArrayList<>();

    Spinner spn_relation;
    ArrayAdapter<String> relation_adapter;
    List<String> relation_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_setting_relative, container, false);
        init();
        getRelative(Glob.Token, Glob.user_id);
        return view;
    }

    public void init() {

        relative_recycler = view.findViewById(R.id.relative_recycler);
        Add_relative = view.findViewById(R.id.Add_relative);


        alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.add_relative_dialog, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();

        dialogClose = dialogLayout.findViewById(R.id.dialogClose);
        txt_add = dialogLayout.findViewById(R.id.txt_add);
        edt_name = dialogLayout.findViewById(R.id.edt_name);
        edt_age = dialogLayout.findViewById(R.id.edt_age);
        edt_gender = dialogLayout.findViewById(R.id.edt_gender);
        edt_blood_group = dialogLayout.findViewById(R.id.edt_blood_group);
        edt_marital_status = dialogLayout.findViewById(R.id.edt_marital_status);

        spn_relation = dialogLayout.findViewById(R.id.spn_relation);


        relation_list = new ArrayList<>();
        relation_list.add("Father");
        relation_list.add("Mother");
        relation_list.add("Brother");
        relation_list.add("Brother-In-Law");
        relation_list.add("Sister");
        relation_list.add("Sister-In-Law");
        relation_list.add("Cousin");
        relation_list.add("Daughter");
        relation_list.add("Daughter-In-Law");
        relation_list.add("Son");
        relation_list.add("Son-In-Law");
        relation_list.add("Uncle");
        relation_list.add("Aunt");


        relation_adapter = new ArrayAdapter<String>(getContext(), R.layout.profile_spinner_text, relation_list);
        relation_adapter.setDropDownViewResource(R.layout.dropdown_item);
        spn_relation.setAdapter(relation_adapter);

        Add_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });

        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

                addRelative(Glob.Token, Glob.user_id, spn_relation.getSelectedItem().toString(),
                        edt_name.getText().toString(), edt_age.getText().toString(), edt_gender.getText().toString(),
                        edt_blood_group.getText().toString(),
                        edt_marital_status.getText().toString());

            }
        });
    }

    public void getRelative(String token, String user_id) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.getRelative(token, user_id).enqueue(new Callback<RelativeModel>() {
            @Override
            public void onResponse(Call<RelativeModel> call, Response<RelativeModel> response) {

                RelativeModel relativeModel = response.body();

                relativeDataList.clear();
                List<RelativeModel.RelativeData> dataList = relativeModel.getRelativeDataList();

                for (int i = 0; i < dataList.size(); i++) {

                    RelativeModel.RelativeData model = dataList.get(i);

                    RelativeModel.RelativeData data = new RelativeModel.RelativeData(model.getRelative_id(),model.getRelative_name(),
                            model.getRelation(), model.getAge(), model.getBlood_group(), model.getMarital_status(),
                            model.getGender());


                    relativeDataList.add(data);
                }
                setRelativeData();


            }

            @Override
            public void onFailure(Call<RelativeModel> call, Throwable t) {

            }
        });
    }

    public void addRelative(String token, String user_id, String relation, String relative_name, String age,
                            String gender, String blood_group, String marital_status) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.addRelative(token, user_id, relation, relative_name, age, gender,
                blood_group, marital_status).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel model = response.body();

                Toast.makeText(getContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }

    public void setRelativeData() {

        relativeAdapter = new RelativeAdapter(relativeDataList, getContext(), new RelativeAdapter.Click() {
            @Override
            public void onItemClick(int position) {


            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        relative_recycler.setLayoutManager(mLayoutManager);
        relativeAdapter.notifyDataSetChanged();
        relative_recycler.setAdapter(relativeAdapter);
    }
}