package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.TestPackagesModel;

import java.util.List;

public class TestPackageAdapter extends RecyclerView.Adapter<TestPackageAdapter.ViewHolder> {

    List<TestPackagesModel.PackagesData> list;
    Context context;
    Click click;

    public interface Click {
        void onBookClick(int position);
    }

    public TestPackageAdapter(List<TestPackagesModel.PackagesData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public TestPackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lab_test_package_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TestPackageAdapter.ViewHolder holder, int position) {

        TestPackagesModel.PackagesData model = list.get(position);

        holder.packageName.setText(model.getPackage_name());
        holder.packageDescription.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr,");


        holder.bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onBookClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, packageDescription, packagePrice, bookNow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookNow = itemView.findViewById(R.id.bookNow);
            packageName = itemView.findViewById(R.id.packageName);
            packageDescription = itemView.findViewById(R.id.packageDescription);
            packagePrice = itemView.findViewById(R.id.packagePrice);

        }
    }
}
