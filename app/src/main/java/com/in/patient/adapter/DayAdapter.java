package com.in.patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    List<String> list;
    Context context;
    Click click;
    int selectedPosition = 0;


    public interface Click {
        void itemClick(int position);
    }

    public DayAdapter(List<String> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        if (selectedPosition == position) {
            holder.day.setTextColor(Color.parseColor("#161616"));
            holder.slot.setTextColor(Color.parseColor("#D68100"));
        }
        else {
            holder.day.setTextColor(Color.parseColor("#757575"));
            holder.slot.setTextColor(Color.parseColor("#757575"));
        }

        String day = list.get(position);
        holder.day.setText(day);



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
        TextView day, slot;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.day);
            slot = itemView.findViewById(R.id.slot);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
