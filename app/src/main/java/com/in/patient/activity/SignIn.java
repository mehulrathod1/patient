package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.SignInModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    Button btnSignIn;
    EditText edtEmail, edtPassword;
    private TextView txtSignUP, dcp_text, in;
    String FMCToken;
    ProgressDialog progressDialog;

    private KeyStore keyStore;
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        init();
//        accessFingerprint();
        getFmcToken();

    }

    public void init() {

        Glob.progressDialog(this);

        txtSignUP = findViewById(R.id.signUP);
        btnSignIn = findViewById(R.id.btnSignIn);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        dcp_text = findViewById(R.id.dcp_text);
        in = findViewById(R.id.in);


        Shader textShader = new LinearGradient(0, 0, in.getPaint().measureText(in.getText().toString()), in.getTextSize(),
                new int[]{Color.parseColor("#233E8B"), Color.parseColor("#1EAE98")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);

        in.getPaint().setShader(textShader);


        Shader textShadedr = new LinearGradient(0, 0, dcp_text.getPaint().measureText(dcp_text.getText().toString()), dcp_text.getTextSize(),
                new int[]{Color.parseColor("#233E8B"), Color.parseColor("#1EAE98")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);

        dcp_text.getPaint().setShader(textShadedr);

        progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edtEmail.getText().toString().equals("")) {
                    edtEmail.setError("Please Enter Email");
                } else if (edtPassword.getText().toString().equals("")) {
                    edtPassword.setError("Please Enter Password");
                } else {

                    signInUser(Token, edtEmail.getText().toString(), edtPassword.getText().toString());
                }
            }
        });

        txtSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });


    }

    public void signInUser(String token, String email, String password) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        progressDialog.show();

        call.signIn(token, email, password).enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {

                SignInModel model = response.body();

                if (model.getStatus().equals("true")) {
                    Toast.makeText(getApplicationContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Glob.user_id = model.getData().getId();

                    SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                    editor.putString("token", "123456789");
                    editor.putString("id", user_id);
                    editor.apply();
                    editor.commit();

                    addFcmToken(Token, user_id, FMCToken);
                    Intent intent = new Intent(getApplicationContext(), Authentication.class);
                    startActivity(intent);
                    Log.e("Signin", "onResponse: " + user_id);
                } else {
                    Toast.makeText(getApplicationContext(), model.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Username and password wrong.", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    public void addFcmToken(String token, String user_id, String fcm_token) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.addFcmToken(token, user_id, fcm_token).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel commonModel = response.body();

                Log.e("signin", "onResponse: " + commonModel.getMessage());

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void accessFingerprint() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (!fingerprintManager.isHardwareDetected()) {
            /**
             * An error message will be displayed if the device does not contain the fingerprint hardware.
             * However if you plan to implement a default authentication method,
             * you can redirect the user to a default authentication activity from here.
             * Example:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            Toast.makeText(getApplicationContext(), "Your Device does not have a Fingerprint Sensor", Toast.LENGTH_SHORT).show();

        } else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Fingerprint authentication permission not enabled", Toast.LENGTH_SHORT).show();
            } else {
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(getApplicationContext(), "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show();

                } else {
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
                        Toast.makeText(getApplicationContext(), "Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();

                    } else {
                        generateKey();


                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerprintHandler helper = new FingerprintHandler(this);
                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


        private Context context;


        // Constructor
        public FingerprintHandler(Context mContext) {
            context = mContext;
        }


        public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }


        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            this.update("Fingerprint Authentication error\n" + errString, false);
        }


        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            this.update("Fingerprint Authentication help\n" + helpString, false);
        }


        @Override
        public void onAuthenticationFailed() {
            this.update("Fingerprint Authentication failed.", false);
        }


        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            this.update("Fingerprint Authentication succeeded.", true);
        }


        public void update(String e, Boolean success) {
//            TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
//            textView.setText(e);
            if (success) {
//                textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
                Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void getFmcToken() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        FMCToken = task.getResult();
                        Log.e("TAG", "onComplete: " + FMCToken);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(SignIn.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}