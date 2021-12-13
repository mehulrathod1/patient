package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.LabTestModel;

import java.util.List;

public class LabTestAdapter extends RecyclerView.Adapter<LabTestAdapter.ViewHolder> {

    List<LabTestModel> list;
    Context context;
    Click click;


    public interface Click {
        void ItemClick(int position);
    }

    public LabTestAdapter(List<LabTestModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lab_test_item, parent, false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LabTestModel model = list.get(position);

        holder.BookingId.setText(model.getBookingId());
        holder.BookingDate.setText(model.getBookingDate());
        holder.AmountPaid.setText(model.getAmountPaid());
        holder.Refund.setText(model.getRefund());
        holder.Amount.setText(model.getAmount());

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

        TextView help, BookingId, BookingDate, AmountPaid, Refund, Amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            BookingId = itemView.findViewById(R.id.BookingId);
            BookingDate = itemView.findViewById(R.id.BookingDate);
            AmountPaid = itemView.findViewById(R.id.AmountPaid);
            Refund = itemView.findViewById(R.id.Refund);
            Amount = itemView.findViewById(R.id.Amount);
            help = itemView.findViewById(R.id.help);

        }
    }
}
