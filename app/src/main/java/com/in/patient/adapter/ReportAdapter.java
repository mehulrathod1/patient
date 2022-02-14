package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.in.patient.R;
import com.in.patient.model.ReportModel;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    List<ReportModel.ReportData> list;
    Context context;
    Click click;

    public interface Click {
        void onViewClick(int position);

        void onDownloadClick(int position);
    }

    public ReportAdapter(List<ReportModel.ReportData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ReportModel.ReportData model = list.get(position);

        holder.report_name.setText(model.getReportfile());


        holder.report_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onViewClick(position);
            }
        });
        holder.report_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onDownloadClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView report_name;
        ImageView report_view, report_download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            report_name = itemView.findViewById(R.id.report_name);
            report_view = itemView.findViewById(R.id.report_view);
            report_download = itemView.findViewById(R.id.report_download);
        }
    }
}
