package com.in.patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.retrofit.TimeSlotItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder> {

    List<TimeSlotItem> list;
    Context context;
    Click click;
    int selectedPosition = -1;


    public interface Click {

        void itemClick(int position);
    }

    public SlotAdapter(List<TimeSlotItem> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_time_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Calendar compDate = Calendar.getInstance();
        SimpleDateFormat for_date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        SimpleDateFormat for_time = new SimpleDateFormat("HH:mm:ss");

        String CurrentDate = for_date.format(compDate.getTime());
        String CurrentDate1 = for_date.format(compDate.getTime());
        String CurrentTime = for_time.format(compDate.getTime());

        Date date1 = null;
        try {
            date1 = for_date.parse(CurrentDate);
            Date date2 = for_date.parse("08-02-2022"+" "+"01:09:20");

            Boolean bool1 = date1.after(date2);
            Boolean bool2 = date1.before(date2);
            Boolean bool3 = date1.equals(date2);

            if (bool1) {
                Log.e("bool", "onResponse: " + (date1 + " is after " + date2));

            } else if (bool2) {
                Log.e("bool", "onResponse: " + (date1 + " is before " + date2));
            } else if (bool3) {
                Log.e("bool", "onResponse: " + (date1 + " is equals to " + date2));
            }

        } catch (ParseException e) {

            e.printStackTrace();
        }

//        int time = Integer.parseInt(list.get(position).getSlotTime().split(":")[0]);
//        int time2 = Integer.parseInt(CurrentTime);

        Log.e("onBindViewHolder", "onBindViewHolder: "+CurrentDate + "----" +CurrentDate1 );



//        if (CurrentDate.equals(CurrentDate1)) {
//
//            if (time <= time2) {
//
//                holder.slot_time.setClickable(false);
//                holder.slot_time.setTextColor(Color.parseColor("#90A4AE"));
//
//            } else {
//
//                holder.slot_time.setTextColor(Color.parseColor("#161616"));
//            }
//
//        }


        if (list.get(position).getSelected() == true) {
            holder.slot_time.setBackgroundColor(Color.parseColor("#1EAE98"));

        } else {

            holder.slot_time.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        TimeSlotItem model = list.get(position);
        holder.slot_time.setText(model.getSlotTime());
        holder.slot_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.itemClick(position);
//                notifyDataSetChanged();

            }
        });

        if (list.get(position).getStatus().equals("booked")) {

            holder.slot_time.setClickable(false);
            holder.slot_time.setTextColor(Color.parseColor("#90A4AE"));

//            holder.slot_time.setBackgroundColor(Color.parseColor("#161616"));

        }

        if (list.get(position).getStatus().equals("availiable")) {
            holder.slot_time.setTextColor(Color.parseColor("#161616"));

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView slot_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            slot_time = itemView.findViewById(R.id.slot_time);
        }
    }
}
