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
import com.in.patient.model.PackageTestModel;

import org.w3c.dom.Text;

import java.util.List;

public class PackageTestAdapter extends RecyclerView.Adapter<PackageTestAdapter.ViewHolder> {

    List<PackageTestModel> list;
    Context context;


    public PackageTestAdapter(List<PackageTestModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_test_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PackageTestModel model = list.get(position);

        holder.packageTestName.setText(model.getPackageTestName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageTestName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            packageTestName = itemView.findViewById(R.id.packageTestName);
        }
    }
}
