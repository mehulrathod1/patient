package com.in.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.MedicineOrderListModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Cart extends Fragment {

    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        inIt();
        getMedicineOrderList(Glob.Token, Glob.user_id);
        return view;


    }


    public void inIt() {
        Glob.progressDialog(getContext());
    }


    public void getMedicineOrderList(String token, String user_id) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getMedicineOrderList(token, user_id).enqueue(new Callback<MedicineOrderListModel>() {
            @Override
            public void onResponse(Call<MedicineOrderListModel> call, Response<MedicineOrderListModel> response) {

                MedicineOrderListModel orderListModel = response.body();
                Glob.dialog.dismiss();
                Toast.makeText(getContext(), "" + orderListModel.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<MedicineOrderListModel> call, Throwable t) {
                Glob.dialog.dismiss();
            }
        });
    }
}