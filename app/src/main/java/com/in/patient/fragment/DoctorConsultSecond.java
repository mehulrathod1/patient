package com.in.patient.fragment;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.activity.PaymentScreen;
import com.in.patient.activity.Search;
import com.in.patient.adapter.DoctorConsultantSecondAdapter;
import com.in.patient.adapter.SpecialistDoctorAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.DoctorConsultantSecondModel;
import com.in.patient.model.SpecialistDoctorModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorConsultSecond extends Fragment {

    RecyclerView recyclerView;
    DoctorConsultantSecondAdapter adapter;
    List<DoctorConsultantSecondModel.ConsultantData> list = new ArrayList<>();

    SpecialistDoctorAdapter specialistDoctorAdapter;
    List<SpecialistDoctorModel.Specialist> specialistList = new ArrayList<>();


    TextView doctor_not_found, maxvalue, minvalue, exp_value, apply_filter;
    ImageView close_dialog;
    BottomSheetDialog bottomSheetDialog;

    View view;
    FloatingActionButton filter;
    LinearLayout searchLayout;

    CrystalRangeSeekbar seekbar;
    CrystalSeekbar expSeekbar;

    List<String> category = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctor_consult_second, container, false);


        String specialist_id = getArguments().getString("specialist_id");
        //Log.e("value", "onCreateView: " + specialist_id);

        init();

        if (specialist_id.equals("0")) {

            getDoctor(Token, user_id);
        } else {
            getSpecialistDoctor(Token, user_id, specialist_id);
        }

        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);
        filter = view.findViewById(R.id.Filter);
        searchLayout = view.findViewById(R.id.searchLayout);
        doctor_not_found = view.findViewById(R.id.doctor_not_found);

        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.filter_dialog);

        try {
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Glob.progressDialog(getContext());


        seekbar = new CrystalRangeSeekbar(getContext());
        expSeekbar = new CrystalSeekbar(getContext());
        seekbar = (CrystalRangeSeekbar) bottomSheetDialog.findViewById(R.id.fee_seekbar);
        expSeekbar = bottomSheetDialog.findViewById(R.id.expSeekbar);
        minvalue = bottomSheetDialog.findViewById(R.id.minvalue);
        maxvalue = bottomSheetDialog.findViewById(R.id.maxvalue);
        exp_value = bottomSheetDialog.findViewById(R.id.exp_value);
        apply_filter = bottomSheetDialog.findViewById(R.id.apply_filter);
        close_dialog = bottomSheetDialog.findViewById(R.id.close_dialog);


        seekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue));
                Log.d("CRS=>", String.valueOf(maxValue));
                minvalue.setText(String.valueOf(minValue));
                maxvalue.setText(String.valueOf(maxValue));

            }
        });
        expSeekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                Log.e("valueChanged", "valueChanged: " + value);

                String str = String.valueOf(value);
                exp_value.setText((str));
            }
        });

        seekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {

            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.show();


            }
        });
        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFilter(Token, user_id, category, "today", "50", "200", "2", "n", "m");
                bottomSheetDialog.dismiss();
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), Search.class);
//                intent.putExtra("Flag","DoctorConsultSecond");
                startActivity(intent);
            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();

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
                            model.getAvailable(), model.getProfile_image()
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

    public void getSpecialistDoctor(String token, String user_id, String specialist_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getSpecialistDoctor(token, user_id, specialist_id).enqueue(new Callback<SpecialistDoctorModel>() {
            @Override
            public void onResponse(Call<SpecialistDoctorModel> call, Response<SpecialistDoctorModel> response) {

                SpecialistDoctorModel specialistDoctorModel = response.body();

                List<SpecialistDoctorModel.Specialist> DataList = specialistDoctorModel.getSpecialistList();

                for (int i = 0; i < DataList.size(); i++) {

                    SpecialistDoctorModel.Specialist model = DataList.get(i);

                    SpecialistDoctorModel.Specialist data = new SpecialistDoctorModel.Specialist(model.getDoctorId(),
                            model.getDoctorName(), model.getExperience(), model.getSpecialist(), model.getLocation(),
                            model.getAvailable(), model.getProfile());

                    specialistList.add(data);
                }
                getSpecialistDoctorData();
                Glob.dialog.dismiss();
                if (specialistList.size() == 0) {
                    doctor_not_found.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<SpecialistDoctorModel> call, Throwable t) {

            }
        });

    }

    public void addFilter(String token, String user_id, List<String> category, String day, String min_fees,
                          String max_fees, String experience,
                          String video_consult, String gender) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.addFilter(token, user_id, category, day, min_fees, max_fees, experience, video_consult, gender).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();

                Toast.makeText(getContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
            }
        });


    }

    public void recyclerData() {

        adapter = new DoctorConsultantSecondAdapter(list, getContext(), new DoctorConsultantSecondAdapter.Click() {
            @Override
            public void onItemClick(int position) {


//
                String doctorId = list.get(position).getUser_id();

                Intent intent = new Intent(getContext(), DoctorProfile.class);
                intent.putExtra("doctorId", doctorId);
//                intent.putExtra("FLag","DoctorConsultSecond");
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    public void getSpecialistDoctorData() {


        specialistDoctorAdapter = new SpecialistDoctorAdapter(specialistList, getContext(), new SpecialistDoctorAdapter.Click() {
            @Override
            public void onItemClick(int position) {

                String doctorId = specialistList.get(position).getDoctorId();
                Intent intent = new Intent(getContext(), DoctorProfile.class);
                intent.putExtra("doctorId", doctorId);
                startActivity(intent);


            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(specialistDoctorAdapter);
    }


}