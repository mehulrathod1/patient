package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.ChatAdapter;
import com.in.patient.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    ChatAdapter adapter;
    RecyclerView recyclerView;
    List<ChatModel> list = new ArrayList<>();
    ImageView nevBack;
    TextView headerTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        init();
        recyclerData();

    }

    public void init() {

        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);

        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Chat");


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    public void recyclerData() {

        ChatModel model = new ChatModel("", "Lorem ipsum", "Lorem ipsum dolor sit amet,");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);

        adapter = new ChatAdapter(list, this, new ChatAdapter.Click() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ChatDashboard.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}