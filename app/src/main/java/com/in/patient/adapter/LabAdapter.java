package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.LabModel;

import java.util.List;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.ViewHolder> {

    List<LabModel> list;
    Context context;
    Click click;

    public interface Click {
        void onItemClick(int position);
    }

    public LabAdapter(List<LabModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lab_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LabModel model = list.get(position);

        holder.LabName.setText(model.getLabName());
        holder.description.setText(model.getDescription());
        holder.Location.setText(model.getLocation());


        holder.viewLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView LabName, description, Location,viewLab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            LabName = itemView.findViewById(R.id.LabName);
            description = itemView.findViewById(R.id.description);
            Location = itemView.findViewById(R.id.Location);
            viewLab = itemView.findViewById(R.id.viewLab);

        }
    }
}
