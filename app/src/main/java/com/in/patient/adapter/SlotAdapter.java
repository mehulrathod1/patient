package com.in.patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.retrofit.TimeSlotItem;

import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder> {

    List<TimeSlotItem> list;
    Context context;
    Click click;
    int selectedPosition = -1;


    public interface Click {

        void itemClick(int position);
    }

    public SlotAdapter(List<TimeSlotItem> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_time_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (selectedPosition == position) {
            holder.slot_time.setTextColor(Color.parseColor("#161616"));

        } else {

            holder.slot_time.setTextColor(Color.parseColor("#1EAE98"));
        }

        TimeSlotItem model = list.get(position);

        holder.slot_time.setText(model.getSlotTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.itemClick(position);
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView slot_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            slot_time = itemView.findViewById(R.id.slot_time);
        }
    }
}
