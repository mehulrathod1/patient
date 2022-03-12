package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.AllTestModel;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    List<AllTestModel.TestData> list ;
    Context context;
    Click click;

    public interface Click{

        void onBookClick(int position);
    }

    public TestAdapter(List<AllTestModel.TestData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AllTestModel.TestData model = list.get(position);

        holder.test_price.setText("100");
        holder.test_description.setText(model.getTest_description());
        holder.test_name.setText(model.getTest_name());

        holder.book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onBookClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView test_name,test_description,test_price,book_now;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            book_now = itemView.findViewById(R.id.book_now);
            test_name = itemView.findViewById(R.id.test_name);
            test_description = itemView.findViewById(R.id.test_description);
            test_price = itemView.findViewById(R.id.test_price);

        }
    }
}
