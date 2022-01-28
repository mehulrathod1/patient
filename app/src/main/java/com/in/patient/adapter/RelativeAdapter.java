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
import com.in.patient.model.RelativeModel;

import java.util.List;


public class RelativeAdapter extends RecyclerView.Adapter<RelativeAdapter.ViewHolder> {

    List<RelativeModel.RelativeData> list;
    Context context;
    Click click;

    public interface Click {
        void onItemClick(int position);
    }

    public RelativeAdapter(List<RelativeModel.RelativeData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.relative_list_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        RelativeModel.RelativeData model = list.get(position);

        holder.relative_name.setText(model.getRelative_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

        TextView relative_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relative_name = itemView.findViewById(R.id.relative_name);
        }
    }
}
