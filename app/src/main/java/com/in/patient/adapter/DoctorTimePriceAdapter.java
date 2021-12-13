package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.in.patient.R;
import com.in.patient.model.DoctorTimePrice;

import java.util.List;

public class DoctorTimePriceAdapter extends RecyclerView.Adapter<DoctorTimePriceAdapter.ViewHolder> {
    List<DoctorTimePrice> list;
    Context context;
    Click click;

    public interface Click {
        void itemClick(int position);
    }

    public DoctorTimePriceAdapter(List<DoctorTimePrice> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_price_per_slot_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorTimePrice model = list.get(position);

        holder.Price.setText(model.getPrice());
        holder.Time.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Time, Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Time = itemView.findViewById(R.id.Time);
            Price = itemView.findViewById(R.id.Price);

        }
    }
}
