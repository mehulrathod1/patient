package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.SignInModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    Button btnSignIn;
    EditText edtEmail, edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        init();
    }

    public void init() {

        Glob.progressDialog(this);

        btnSignIn = findViewById(R.id.btnSignIn);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
//                signInUser(Token, edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    public void signInUser(String token, String email, String password) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();

        call.signIn(token, email, password).enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {

                SignInModel model = response.body();

                if (model.getStatus().equals("true")) {
                    Toast.makeText(getApplicationContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                    Glob.dialog.dismiss();
                    Glob.user_id = model.getData().getId();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Log.e("Signin", "onResponse: " + user_id);
                } else {
                    Toast.makeText(getApplicationContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                    Glob.dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailure: " + t.getMessage());
                Glob.dialog.dismiss();
            }
        });

    }


}