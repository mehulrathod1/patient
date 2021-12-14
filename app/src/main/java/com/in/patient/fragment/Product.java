package com.in.patient.fragment;

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
import com.in.patient.model.MedicinesModel;
import com.in.patient.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class Product extends Fragment {


    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<ProductModel> list = new ArrayList<>();
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
        recyclerData();
        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.recycler);

    }

    public void recyclerData() {

        ProductModel model = new ProductModel("Product Name", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et.", "$199");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        adapter = new ProductAdapter(list, getContext(), new ProductAdapter.Click() {
            @Override
            public void ItemClick(int position) {


                Fragment fragment = new ProductDetail();
                loadFragment(fragment);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
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