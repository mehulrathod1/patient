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
import com.in.patient.model.LabTestModel;
import com.in.patient.model.MedicinesModel;
import com.in.patient.model.MedicinesOrderModel;

import java.util.List;

public class MedicinesOrderAdapter extends RecyclerView.Adapter<MedicinesOrderAdapter.ViewHolder> {

    List<MedicinesOrderModel> list;
    Context context;
    Click click;

    public interface Click {
        void ItemClick(int position);
    }

    public MedicinesOrderAdapter(List<MedicinesOrderModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_order_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MedicinesOrderModel model = list.get(position);

        holder.OrderId.setText(model.getOrderId());
        holder.OrderDate.setText(model.getOrderDate());
        holder.OrderAddress.setText(model.getOrderAddress());
        holder.AmountPaid.setText(model.getAmountPaid());

        holder.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.ItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView OrderId, OrderDate, OrderAddress, AmountPaid, help;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderId = itemView.findViewById(R.id.OrderId);
            OrderDate = itemView.findViewById(R.id.OrderDate);
            OrderAddress = itemView.findViewById(R.id.OrderAddress);
            AmountPaid = itemView.findViewById(R.id.AmountPaid);
            help = itemView.findViewById(R.id.help);

        }
    }
}
