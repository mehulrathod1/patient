package com.in.patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.fragment.Medicines;
import com.in.patient.model.MedicinesModel;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.ViewHolder> {
    List<MedicinesModel> list;
    Context context;
    Click click;


    public interface Click {
        void OnItemClick(int position);
    }

    public MedicinesAdapter(List<MedicinesModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicines_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MedicinesModel model = list.get(position);

        holder.MedicineName.setText(model.getMedicineName());
        holder.MedicineDescription.setText(model.getMedicineDescription());
        holder.MedicinePrice.setText(model.getMedicinePrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView MedicineName, MedicineDescription, MedicinePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MedicineName = itemView.findViewById(R.id.MedicineName);
            MedicineDescription = itemView.findViewById(R.id.MedicineDescription);
            MedicinePrice = itemView.findViewById(R.id.MedicinePrice);
        }
    }
}
