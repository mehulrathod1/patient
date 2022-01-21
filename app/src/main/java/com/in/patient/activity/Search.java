package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.adapter.MyAppointmentAdapter;
import com.in.patient.adapter.OrderAdapter;
import com.in.patient.adapter.SearchItemAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.OrderModel;
import com.in.patient.model.SearchModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {

    ImageView nevBack;
    TextView headerTitle;
    TextView doctorName;
    EditText search_name;


    SearchItemAdapter adapter;
    RecyclerView recyclerView;
    List<SearchModel.SearchData> list = new ArrayList<>();

    String Flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();
        init();
        getSearchItem(Glob.Token, Glob.user_id, search_name.getText().toString());


        Intent intent = getIntent();
        Flag = intent.getStringExtra("Flag");

    }

    public void init() {

        Glob.progressDialog(this);
        nevBack = findViewById(R.id.nevBack);
        headerTitle = findViewById(R.id.header_title);
        doctorName = findViewById(R.id.doctorName);
        recyclerView = findViewById(R.id.recycler);
        search_name = findViewById(R.id.search_name);

        nevBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                if (Flag.equals("DoctorConsultSecond")){
                    intent.putExtra("s", 2);
                    startActivity(intent);
                    finish();

                }
                if (Flag.equals("MainActivity")){
                    startActivity(intent);
                    finish();

                }



            }
        });

        search_name.addTextChangedListener(new TextWatcher() {

                                                       @Override
                                                       public void afterTextChanged(Editable s) {


                                                       }

                                                       @Override
                                                       public void beforeTextChanged(CharSequence s, int start,
                                                                                     int count, int after) {
                                                       }

                                                       @Override
                                                       public void onTextChanged(CharSequence s, int start,
                                                                                 int before, int count) {
                                                           if (s.length() != 0) {

                                                               Log.e("onTextChanged", "onTextChanged: " + search_name.getText().toString());
                                                               getSearchItem(Glob.Token, Glob.user_id, search_name.getText().toString());


                                                           }
                                                       }
                                                   });


//        doctorName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("s", 2);
//                startActivity(intent);
//            }
//        });
    }

    public void getSearchItem(String token, String user_id, String keyword) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);


        call.getSearchItem(token, user_id, keyword).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {


                list.clear();
                SearchModel searchModel = response.body();

                List<SearchModel.SearchData> DataList = searchModel.getSearchDataList();

                for (int i = 0; i < DataList.size(); i++) {

                    SearchModel.SearchData model = DataList.get(i);
                    SearchModel.SearchData data = new SearchModel.SearchData(model.getDoctor_id(), model.getDoctor_name());
                    list.add(data);

                }

                recyclerData();

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                Glob.dialog.dismiss();

            }
        });

    }

    public void recyclerData() {

        adapter = new SearchItemAdapter(list, getApplicationContext(), new SearchItemAdapter.Click() {
            @Override
            public void itemClick(int position) {
                String doctorId = list.get(position).getDoctor_id();
                Intent intent = new Intent(getApplicationContext(), DoctorProfile.class);
                intent.putExtra("doctorId", doctorId);
                intent.putExtra("FLag","Search");
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
