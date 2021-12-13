package com.in.patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.QuestionModel;

import java.util.List;

public class MyQuestionAdapter extends RecyclerView.Adapter<MyQuestionAdapter.ViewHolder> {

    List<QuestionModel> list;
    Context context;
    Click click;


    public interface Click {
        void ItemClick(int position);
    }

    public MyQuestionAdapter(List<QuestionModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_question_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        QuestionModel model = list.get(position);

        holder.Question.setText(model.getQuestion());
        holder.Date.setText(model.getDate());
        holder.QuestionDescription.setText(model.getQuestionDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        TextView Question, Date, QuestionDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Question = itemView.findViewById(R.id.Question);
            Date = itemView.findViewById(R.id.Date);
            QuestionDescription = itemView.findViewById(R.id.QuestionDescription);
        }
    }
}
