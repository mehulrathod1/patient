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
import com.in.patient.model.ServiceCategoryModel;

import java.util.List;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder> {
    List<ServiceCategoryModel> list;
    Context context;
    Click click;


    public interface Click {
        void onItemClick(int position);
    }

    public ServiceCategoryAdapter(List<ServiceCategoryModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_category_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ServiceCategoryModel model = list.get(position);

        holder.CategoryName.setText(model.getCategoryName());
        holder.Amount.setText(model.getAmount());
        holder.description.setText(model.getDescription());


        holder.BookAppointment.setOnClickListener(new View.OnClickListener() {
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

        TextView CategoryName, Amount, description,BookAppointment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CategoryName = itemView.findViewById(R.id.CategoryName);
            Amount = itemView.findViewById(R.id.Amount);
            description = itemView.findViewById(R.id.description);
            BookAppointment = itemView.findViewById(R.id.BookAppointment);

        }
    }
}
