package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.ChatAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.ChatModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {

    ChatAdapter adapter;
    RecyclerView recyclerView;
    List<ChatModel.ChatDoctorList> ChatList = new ArrayList<>();
    ImageView nevBack;
    TextView headerTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        init();
        getChatList(Glob.Token,Glob.user_id);


    }

    public void init() {

        Glob.progressDialog(this);
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);

        recyclerView = findViewById(R.id.recycler);

        headerTitle.setText("My Chat");


        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    public void getChatList(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getChatList(token, user_id).enqueue(new Callback<ChatModel>() {
            @Override
            public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {

                ChatModel chatModel = response.body();

                List<ChatModel.ChatDoctorList> dataList = chatModel.getChatDoctorLists();

                for (int i=0;i<dataList.size();i++){

                    ChatModel.ChatDoctorList model = dataList.get(i);
                    ChatModel.ChatDoctorList data = new ChatModel.ChatDoctorList(model.getUser_id(),model.getUser_name(),model.getProfile_image(),model.getMessage());

                    ChatList.add(data);
                    Glob.dialog.dismiss();
                }
                recyclerData();

            }

            @Override
            public void onFailure(Call<ChatModel> call, Throwable t) {

                Log.e("onFailure", "onFailure: "+t.getMessage());
                Glob.dialog.dismiss();

            }
        });

    }

    public void recyclerData() {


        adapter = new ChatAdapter(ChatList, this, new ChatAdapter.Click() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ChatDashboard.class);
                intent.putExtra("doctor_id",ChatList.get(position).getUser_id());
                intent.putExtra("doctor_name",ChatList.get(position).getUser_name());
                intent.putExtra("doctor_image",ChatList.get(position).getProfile_image());
                startActivity(intent);
            }
        });

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
    }
}