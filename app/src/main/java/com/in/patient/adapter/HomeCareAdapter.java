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
import com.in.patient.model.HomeCareModel;

import org.w3c.dom.Text;

import java.util.List;

public class HomeCareAdapter extends RecyclerView.Adapter<HomeCareAdapter.ViewHolder> {

    List<HomeCareModel.CareData> list;
    Context context;
    Click click;

    public interface Click {
        void onItemClick(int position);
    }

    public HomeCareAdapter(List<HomeCareModel.CareData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_care_checkup, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HomeCareModel.CareData model = list.get(position);

        holder.text.setText(model.getServiceName());
        Glide.with(context).load(model.getImage()).into(holder.image);


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
        ImageView image;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);
        }
    }
}
