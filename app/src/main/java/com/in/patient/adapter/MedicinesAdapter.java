package com.in.patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.patient.R;
import com.in.patient.fragment.Medicines;
import com.in.patient.model.MedicinesModel;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.ViewHolder> {
    List<MedicinesModel.MedicinesData> list;
    Context context;
    Click click;


    public interface Click {
        void OnItemClick(int position);
    }

    public MedicinesAdapter(List<MedicinesModel.MedicinesData> list, Context context, Click click) {
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

        MedicinesModel.MedicinesData model = list.get(position);

        holder.MedicineName.setText(model.getMedicines_name());
        holder.MedicineDescription.setText(model.getDescription());
        holder.MedicinePrice.setText(model.getPrice());

        Glide.with(context)
                .load(model.getMedicine_image())
                .into(holder.medicinesImage);

        holder.AddToCart.setOnClickListener(new View.OnClickListener() {
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

        TextView MedicineName, MedicineDescription, MedicinePrice, AddToCart;
        ImageView medicinesImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MedicineName = itemView.findViewById(R.id.MedicineName);
            MedicineDescription = itemView.findViewById(R.id.MedicineDescription);
            MedicinePrice = itemView.findViewById(R.id.MedicinePrice);
            AddToCart = itemView.findViewById(R.id.AddToCart);
            medicinesImage = itemView.findViewById(R.id.medicinesImage);
        }
    }
}
