package com.in.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.patient.R;
import com.in.patient.adapter.HomeCareAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.HomeCareModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeCareCategoryFragment extends Fragment {

    View view;
    RecyclerView healthCareRecycler;

    List<HomeCareModel.CareData> careDataList = new ArrayList<>();
    HomeCareAdapter homeCareAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_care_category, container, false);

        init();
        getHomeCareServices(Glob.Token, Glob.user_id);

        return view;
    }

    public void init() {

        Glob.progressDialog(getContext());
        healthCareRecycler = view.findViewById(R.id.healthCareRecycler);

    }


    public void getHomeCareServices(String token, String userId) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getHomeCareServices(token, userId).enqueue(new Callback<HomeCareModel>() {
            @Override
            public void onResponse(Call<HomeCareModel> call, Response<HomeCareModel> response) {

                HomeCareModel homeCareModel = response.body();

                List<HomeCareModel.CareData> dataList = homeCareModel.getCareDataList();
                for (int i = 0; i < dataList.size(); i++) {

                    HomeCareModel.CareData model = dataList.get(i);

                    HomeCareModel.CareData data = new HomeCareModel.CareData(
                            model.getService_id(), model.getServiceName(), model.getImage());


                    careDataList.add(data);
                }
                homeCareData();
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<HomeCareModel> call, Throwable t) {
                Glob.dialog.dismiss();
            }
        });
    }


    public void homeCareData() {


        homeCareAdapter = new HomeCareAdapter(careDataList, getContext(), new HomeCareAdapter.Click() {
            @Override
            public void onItemClick(int position) {

                Fragment fragment = new HomeCareSubCategory();
                loadFragment(fragment);


            }
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        healthCareRecycler.setLayoutManager(mLayoutManager);
        healthCareRecycler.setAdapter(homeCareAdapter);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}