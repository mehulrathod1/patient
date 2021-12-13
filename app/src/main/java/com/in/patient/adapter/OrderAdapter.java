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
import com.in.patient.model.MedicinesOrderModel;
import com.in.patient.model.OrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolde> {

    List<OrderModel> list;
    Context context;
    Click click;

    public interface Click {
        void ItemClick(int position);
    }

    public OrderAdapter(List<OrderModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_order_item, parent, false);
        return new ViewHolde(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, @SuppressLint("RecyclerView") int position) {

        OrderModel model = list.get(position);

        holder.ProductName.setText(model.getProductName());
        holder.OrderDate.setText(model.getOrderDate());
        holder.AmountPaid.setText(model.getAmountPaid());
        holder.OrderStatus.setText(model.getOrderStatus());
        holder.OrderId.setText(model.getOrderId());
        holder.OrderDetail.setText(model.getOrderDetail());
        holder.DeliveryAddress.setText(model.getDeliveryAddress());
        holder.PaymentStatus.setText(model.getPaymentStatus());

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

    public class ViewHolde extends RecyclerView.ViewHolder {

        TextView ProductName, PaymentStatus, OrderDate, AmountPaid, OrderStatus, OrderId, OrderDetail, DeliveryAddress, help;

        public ViewHolde(@NonNull View itemView) {
            super(itemView);

            ProductName = itemView.findViewById(R.id.ProductName);
            OrderDate = itemView.findViewById(R.id.OrderDate);
            AmountPaid = itemView.findViewById(R.id.AmountPaid);
            OrderStatus = itemView.findViewById(R.id.OrderStatus);
            OrderId = itemView.findViewById(R.id.OrderId);
            OrderDetail = itemView.findViewById(R.id.OrderDetail);
            DeliveryAddress = itemView.findViewById(R.id.DeliveryAddress);
            help = itemView.findViewById(R.id.help);
            PaymentStatus = itemView.findViewById(R.id.PaymentStatus);
        }
    }
}
