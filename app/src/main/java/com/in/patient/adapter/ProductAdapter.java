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
import com.in.patient.fragment.Product;
import com.in.patient.globle.Glob;
import com.in.patient.model.OrderModel;
import com.in.patient.model.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ProductModel.DataItem> list;
    Context context;
    Click click;


    public interface Click {
        void ItemClick(int position);
    }

    public ProductAdapter(List<ProductModel.DataItem> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ProductModel.DataItem model = list.get(position);

        holder.ProductName.setText(model.getProductName());
        holder.description.setText(model.getProductDetails());
        holder.Price.setText(model.getProductPrice());


        holder.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.ItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ProductName, description, Price, buyNow;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ProductName = itemView.findViewById(R.id.ProductName);
            description = itemView.findViewById(R.id.description);
            Price = itemView.findViewById(R.id.Price);
            buyNow = itemView.findViewById(R.id.buyNow);
            productImage = itemView.findViewById(R.id.productImage);

        }
    }
}
