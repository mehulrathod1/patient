package com.in.patient.adapter;

import android.annotation.SuppressLint;
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
import com.in.patient.model.MyReviewModel;

import java.util.List;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder> {

    List<MyReviewModel.ReviewData> list;
    Context context;
    Click click;

    public MyReviewAdapter(List<MyReviewModel.ReviewData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    public interface Click {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_reviw_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyReviewModel.ReviewData model = list.get(position);

        holder.Name.setText(model.getUserDetail().getPatient_name());
        holder.Date.setText(model.getDate());
        holder.ReviewText.setText(model.getMessage());

        Glide.with(context)
                .load(model.getUserDetail().getPatient_image())
                .into(holder.profileImage);


        if (model.getRating().equals("1")) {
            holder.rate2.setVisibility(View.GONE);
            holder.rate3.setVisibility(View.GONE);
            holder.rate4.setVisibility(View.GONE);
            holder.rate5.setVisibility(View.GONE);
        } else if (model.getRating().equals("2")) {
            holder.rate3.setVisibility(View.GONE);
            holder.rate4.setVisibility(View.GONE);
            holder.rate5.setVisibility(View.GONE);
        } else if (model.getRating().equals("3")) {
            holder.rate4.setVisibility(View.GONE);
            holder.rate5.setVisibility(View.GONE);
        } else if (model.getRating().equals("4")) {
            holder.rate5.setVisibility(View.GONE);
        }
        else if (model.getRating().equals("5")){

            holder.rate1.setVisibility(View.VISIBLE);
            holder.rate2.setVisibility(View.VISIBLE);
            holder.rate3.setVisibility(View.VISIBLE);
            holder.rate4.setVisibility(View.VISIBLE);
            holder.rate5.setVisibility(View.VISIBLE);

        }
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

        TextView Name, Date, ReviewText;
        ImageView profileImage, rate1, rate2, rate3, rate4, rate5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.Name);
            Date = itemView.findViewById(R.id.Date);
            ReviewText = itemView.findViewById(R.id.ReviewText);

            profileImage = itemView.findViewById(R.id.profileImage);
            rate1 = itemView.findViewById(R.id.rate1);
            rate2 = itemView.findViewById(R.id.rate2);
            rate3 = itemView.findViewById(R.id.rate3);
            rate4 = itemView.findViewById(R.id.rate4);
            rate5 = itemView.findViewById(R.id.rate5);
        }
    }
}

