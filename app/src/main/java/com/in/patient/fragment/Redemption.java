package com.in.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Redemption extends Fragment {

    View view;
    TextView withdrawal_amount;
    Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_redemption, container, false);

        init();
        return view;
    }

    public void init() {

        withdrawal_amount = view.findViewById(R.id.withdrawal_amount);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (withdrawal_amount.getText().toString().equals("")) {
                    withdrawal_amount.setError("Please Enter Amount");
                } else {

                    withdrawalRequest(Glob.Token, Glob.user_id, withdrawal_amount.getText().toString());
                }
            }
        });

    }

    public void withdrawalRequest(String token, String user_id, String amount) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.withdrawalRequest(token, user_id, amount).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();

                Toast.makeText(getContext(), "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }


}