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
    List<TransactionHistoryModel.TransactionHistoryData> list;
    Context context;

    public TransactionHistoryAdapter(List<TransactionHistoryModel.TransactionHistoryData> list, Context context) {
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

        TransactionHistoryModel.TransactionHistoryData model = list.get(position);
        holder.TransactionId.setText(model.getTxn_id());
        holder.TransactionDate.setText(model.getTxn_date());
        holder.OrderId.setText(model.getId());
        holder.TransactionAmount.setText(model.getAmount());

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
