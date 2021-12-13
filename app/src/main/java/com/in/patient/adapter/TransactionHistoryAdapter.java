package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.in.patient.R;
import com.in.patient.model.TransactionHistoryModel;

import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    List<TransactionHistoryModel> list;
    Context context;

    public TransactionHistoryAdapter(List<TransactionHistoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_history_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TransactionHistoryModel model = list.get(position);
        holder.TransactionId.setText(model.getTransactionId());
        holder.TransactionDate.setText(model.getTransactionDate());
        holder.OrderId.setText(model.getOrderId());
        holder.TransactionAmount.setText(model.getTransactionAmount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TransactionId, TransactionDate, OrderId, TransactionAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TransactionId = itemView.findViewById(R.id.Transaction_Id);
            TransactionDate = itemView.findViewById(R.id.TransactionDate);
            OrderId = itemView.findViewById(R.id.OrderId);
            TransactionAmount = itemView.findViewById(R.id.TransactionAmount);
        }
    }
}
