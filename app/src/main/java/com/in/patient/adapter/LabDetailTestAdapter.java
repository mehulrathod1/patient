package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.AllTestModel;
import com.in.patient.model.LabTestModel;

import java.util.List;

public class LabDetailTestAdapter extends RecyclerView.Adapter<LabDetailTestAdapter.ViewHolder> {

    List<AllTestModel.TestData> list;
    Context context;



    public LabDetailTestAdapter(List<AllTestModel.TestData> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lab_detail_test_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AllTestModel.TestData model = list.get(position);

        holder.testName.setText(model.getTest_name());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox testName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            testName = itemView.findViewById(R.id.testName);
        }
    }
}
