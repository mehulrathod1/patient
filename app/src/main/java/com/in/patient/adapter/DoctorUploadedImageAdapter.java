package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.in.patient.R;
import com.in.patient.model.CareAndCheckupModel;

import java.util.List;

public class DoctorUploadedImageAdapter extends RecyclerView.Adapter<DoctorUploadedImageAdapter.ViewHolder> {
    List<CareAndCheckupModel> list;
    Context context;
    Click click;

    public interface Click {
        void onClick(int position);
    }

    public DoctorUploadedImageAdapter(List<CareAndCheckupModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctot_uploaad_image_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CareAndCheckupModel model = list.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
