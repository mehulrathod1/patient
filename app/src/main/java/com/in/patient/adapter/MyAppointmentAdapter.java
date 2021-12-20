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
import com.in.patient.model.MyAppointmentModel;

import java.util.List;

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> {

    List<MyAppointmentModel.AppointmentData> list;
    Context context;
    Click click;

    public interface Click {
        void onButtonClick(int position);
    }

    public MyAppointmentAdapter(List<MyAppointmentModel.AppointmentData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_appointment_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyAppointmentModel.AppointmentData model = list.get(position);

        holder.BookingId.setText(model.getBooingId());
        holder.DoctorName.setText(model.getDoctorName());
        holder.CityName.setText(model.getLocation());
        holder.Price.setText(model.getFees());
        holder.Status.setText(model.getStatus());

        Glide.with(context)
                .load(model.getProfile())
                .into(holder.profileImage);

        holder.viewBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onButtonClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView BookingId, DoctorName, CityName, Price, viewBookingDetail, Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            profileImage = itemView.findViewById(R.id.profileImage);
            BookingId = itemView.findViewById(R.id.BookingId);
            DoctorName = itemView.findViewById(R.id.DoctorName);
            CityName = itemView.findViewById(R.id.CityName);
            Price = itemView.findViewById(R.id.Price);
            Status = itemView.findViewById(R.id.Status);
            viewBookingDetail = itemView.findViewById(R.id.viewBookingDetail);

        }
    }
}
