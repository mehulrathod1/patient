package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.patient.R;
import com.in.patient.model.SpecialistDoctorModel;

import java.util.List;

public class SpecialistDoctorAdapter extends RecyclerView.Adapter<SpecialistDoctorAdapter.ViewHolder> {
    List<SpecialistDoctorModel.Specialist> list;
    Context context;
    Click click;

    public interface Click {
        void onItemClick(int position);
    }

    public SpecialistDoctorAdapter(List<SpecialistDoctorModel.Specialist> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_consultant_second_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SpecialistDoctorModel.Specialist model = list.get(position);

        holder.DoctorName.setText(model.getDoctorName());
        holder.exp.setText(model.getExperience());
//        holder.LikePercentage.setText(model.getLikePercentage());
//        holder.Rate.setText(model.getRate());
        holder.speciality.setText(model.getSpecialist());
        holder.Location.setText(model.getLocation());
        holder.available.setText(model.getAvailable());


        Glide.with(context)
                .load(model.getProfile())
                .into(holder.profileImage);

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
        TextView speciality, exp, Location, LikePercentage, Rate, available, DoctorName, BookAppointment;
        ImageView profileImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            DoctorName = itemView.findViewById(R.id.DoctorName);
            speciality = itemView.findViewById(R.id.speciality);
            exp = itemView.findViewById(R.id.exp);
            Location = itemView.findViewById(R.id.Location);
            LikePercentage = itemView.findViewById(R.id.LikePercentage);
            Rate = itemView.findViewById(R.id.Rate);
            BookAppointment = itemView.findViewById(R.id.BookAppointment);
            available = itemView.findViewById(R.id.available);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
