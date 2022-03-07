package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.CouponModel;

import org.w3c.dom.Text;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    List<CouponModel.CouponList> list;
    Context context;
    Click click;

    public interface Click {

        void onItemClick(int position);
    }

    public CouponAdapter(List<CouponModel.CouponList> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        CouponModel.CouponList model = list.get(position);


        holder.couponCode.setText(model.getCoupon_code());
        holder.couponTitle.setText(model.getTitle());


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

        TextView couponTitle, couponCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            couponCode = itemView.findViewById(R.id.couponCode);
            couponTitle = itemView.findViewById(R.id.couponTitle);
        }
    }
}
