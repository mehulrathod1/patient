package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.in.patient.R;
import com.in.patient.model.CareAndCheckupModel;
import com.in.patient.model.ClinicImage;

import java.util.List;

public class DoctorUploadedImageAdapter extends RecyclerView.Adapter<DoctorUploadedImageAdapter.ViewHolder> {
    List<ClinicImage> list;
    Context context;
    Click click;

    public interface Click {
        void onClick(int position);
    }

    public DoctorUploadedImageAdapter(List<ClinicImage> list, Context context, Click click) {
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

        ClinicImage model = list.get(position);


        Glide.with(context)
                .load(model.getClinic_images())
                .into(holder.ClinicImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ClinicImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ClinicImage = itemView.findViewById(R.id.UploadImage);
        }
    }
}
