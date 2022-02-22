package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.patient.R;
import com.in.patient.adapter.ChatDashboardAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.ChatDashboardModel;
import com.in.patient.model.ChatModel;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDashboard extends AppCompatActivity {


    RecyclerView chatRecycler;
    ChatDashboardAdapter chatDashboardAdapter;
    List<ChatDashboardModel.DashboardMessage> chatList = new ArrayList<>();

    ImageView profileImage;
    TextView profileName;
    String doctor_id, doctor_name, doctor_image;

    LinearLayout sendMessage;
    EditText messageText;

    Handler handler = new Handler();
    Runnable runnable;
    long delay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dashboard);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        doctor_id = intent.getStringExtra("doctor_id");
        doctor_name = intent.getStringExtra("doctor_name");
        doctor_image = intent.getStringExtra("doctor_image");


        init();
        clickEvent();
        getChatMessage(Glob.Token, Glob.user_id, doctor_id);
        scheduleSendLocation();


    }

    public void init() {


        Glob.progressDialog(this);
        chatRecycler = findViewById(R.id.chatRecycler);
        profileName = findViewById(R.id.doctor_name);
        profileImage = findViewById(R.id.profileImage);
        sendMessage = findViewById(R.id.sendMessage);
        messageText = findViewById(R.id.messageText);

        Glide.with(this).load(doctor_image).into(profileImage);
        profileName.setText(doctor_name);
    }

    public void clickEvent() {
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageText.getText().toString();
                String ServerUnicodeEncodedMessage = StringEscapeUtils.escapeJava(message);

                if (message.equals("")) {
                } else {
                    sendMessage(Glob.Token, Glob.user_id, doctor_id, "m", ServerUnicodeEncodedMessage);
                }
            }
        });
    }

    public void getChatMessage(String token, String user_id, String doctor_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
//        Glob.dialog.show();

        call.getChatMessage(token, user_id, doctor_id).enqueue(new Callback<ChatDashboardModel>() {
            @Override
            public void onResponse(Call<ChatDashboardModel> call, Response<ChatDashboardModel> response) {

                chatList.clear();
                ChatDashboardModel chatDashboardModel = response.body();
                List<ChatDashboardModel.DashboardMessage> dataList = chatDashboardModel.getDashboardMessageList();

                for (int i = 0; i < dataList.size(); i++) {

                    ChatDashboardModel.DashboardMessage model = dataList.get(i);

                    ChatDashboardModel.DashboardMessage data = new ChatDashboardModel.DashboardMessage(
                            model.getDoctor_id(), model.getPatient_id(), model.getSend_by(),
                            model.getMessage(), model.getDate(), model.getTime());

                    chatList.add(data);
//                    Glob.dialog.dismiss();
                }
                setData();
            }

            @Override
            public void onFailure(Call<ChatDashboardModel> call, Throwable t) {
//                Glob.dialog.dismiss();
            }
        });
    }

    public void sendMessage(String token, String user_id, String doctor_id, String msg_type, String message) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.sendMessage(token, user_id, doctor_id, msg_type, message).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();

                getChatMessage(Glob.Token, Glob.user_id, doctor_id);
                messageText.setText("");
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }

    public void setData() {


        chatDashboardAdapter = new ChatDashboardAdapter(chatList, getApplicationContext(), new ChatDashboardAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        chatRecycler.setLayoutManager(mLayoutManager);
        chatDashboardAdapter.notifyDataSetChanged();
        chatRecycler.setAdapter(chatDashboardAdapter);

    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
        super.onPause();
    }

    public void scheduleSendLocation() {

        handler.postDelayed(new Runnable() {
            public void run() {

//                getChatList2(chatId, User_Id);
//                int x = realTimeList.size();
//                int y = list.size();
//                if (x > y) {
//                    realTimeList = list;

                getChatMessage(Glob.Token, Glob.user_id, doctor_id);

//                }

                handler.postDelayed(this, delay);

            }

        }, delay);
    }

}
