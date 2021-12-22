package com.in.patient.fragment;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

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
import com.in.patient.adapter.MedicinesAdapter;
import com.in.patient.adapter.ProductAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.MedicinesModel;
import com.in.patient.model.ProductModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends Fragment {


    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<ProductModel.DataItem> list = new ArrayList<>();
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);
        init();
        getProduct(Token, user_id);
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);

        Glob.progressDialog(getContext());
    }


    public void getProduct(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.getProduct(token, user_id).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {

                ProductModel productModel = response.body();

                List<ProductModel.DataItem> DataList = productModel.getData();

                for (int i = 0; i < DataList.size(); i++) {

                    ProductModel.DataItem model = DataList.get(i);

                    ProductModel.DataItem Data = new ProductModel.DataItem(
                            "$ "+ model.getProductPrice(), model.getProductDetails(),
                            model.getProductName()
                    );

                    list.add(Data);
                }
                productData();
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {

            }
        });


    }

    public void productData() {

        adapter = new ProductAdapter(list, getContext(), new ProductAdapter.Click() {
            @Override
            public void ItemClick(int position) {


                Fragment fragment = new ProductDetail();
                loadFragment(fragment);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}