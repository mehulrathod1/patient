package com.in.patient.activity;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.in.patient.R;
import com.in.patient.adapter.ChatDashboardAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.ChatDashboardModel;
import com.in.patient.model.ChatModel;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import net.gotev.uploadservice.BuildConfig;
import net.gotev.uploadservice.UploadServiceConfig;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDashboard extends AppCompatActivity {


    String TAG = "ChatDashboard";
    RecyclerView chatRecycler;
    ChatDashboardAdapter chatDashboardAdapter;
    List<ChatDashboardModel.DashboardMessage> chatList = new ArrayList<>();
    List<ChatDashboardModel.DashboardMessage> dummyChatList = new ArrayList<>();

    ImageView profileImage, addImage;
    TextView profileName;
    String doctor_id, doctor_name, doctor_image;

    LinearLayout sendMessage, select_from_gallery, pik_from_camera, video;
    RelativeLayout ChatLayout;
    EditText messageText;
    PopupWindow popupWindow;
    BottomSheetDialog bottomSheetDialog;


    Handler handler = new Handler();
    Runnable runnable;
    long delay = 2000;

    File photoFile, img_file;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    public String photoFileName = "IMG_" + timeStamp + ".jpg";
    Uri img_url;
    Uri uri;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_Gallery_REQUEST_CODE = 101;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;

    int chatListSize = 0;

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
        setData();
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
        addImage = findViewById(R.id.addImage);
        ChatLayout = findViewById(R.id.ChatLayout);


        bottomSheetDialog = new BottomSheetDialog(ChatDashboard.this);
        bottomSheetDialog.setContentView(R.layout.chat_select_image_popup);

        select_from_gallery = bottomSheetDialog.findViewById(R.id.gallery);
        pik_from_camera = bottomSheetDialog.findViewById(R.id.camera);
        video = bottomSheetDialog.findViewById(R.id.video);

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

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.show();
            }
        });

        select_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMediaContent();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                openMediaContent();

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                    try {
                        Intent intent = new Intent();
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("video/*");
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        startActivityForResult(intent, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                    } catch (Exception e) {
                    }
                } else {
                    ActivityCompat.requestPermissions(ChatDashboard.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                    Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
                }


            }
        });
        pik_from_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera();

            }
        });

    }

    public void getChatMessage(String token, String user_id, String doctor_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
//        Glob.dialog.show();

        call.getChatMessage(token, user_id, doctor_id).enqueue(new Callback<ChatDashboardModel>() {
            @Override
            public void onResponse(Call<ChatDashboardModel> call, Response<ChatDashboardModel> response) {

                ChatDashboardModel chatDashboardModel = response.body();
                chatList.clear();
                List<ChatDashboardModel.DashboardMessage> dataList = chatDashboardModel.getDashboardMessageList();

                for (int i = 0; i < dataList.size(); i++) {

                    ChatDashboardModel.DashboardMessage model = dataList.get(i);

                    ChatDashboardModel.DashboardMessage data = new ChatDashboardModel.DashboardMessage(
                            model.getDoctor_id(), model.getPatient_id(), model.getSend_by(),
                            model.getMessage(), model.getDate(), model.getTime(), model.getChat_image(), model.getChat_video());

                    chatList.add(data);
//                    Glob.dialog.dismiss();
                }

                chatDashboardAdapter.notifyDataSetChanged();
                chatRecycler.scrollToPosition(chatDashboardAdapter.getItemCount() - 1);


            }

            @Override
            public void onFailure(Call<ChatDashboardModel> call, Throwable t) {
//                Glob.dialog.dismiss();
            }
        });
    }

    public void getChatMessageDummy(String token, String user_id, String doctor_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
//        Glob.dialog.show();

        call.getChatMessage(token, user_id, doctor_id).enqueue(new Callback<ChatDashboardModel>() {
            @Override
            public void onResponse(Call<ChatDashboardModel> call, Response<ChatDashboardModel> response) {

                ChatDashboardModel chatDashboardModel = response.body();
                chatList.clear();
                List<ChatDashboardModel.DashboardMessage> dataList = chatDashboardModel.getDashboardMessageList();

                for (int i = 0; i < dataList.size(); i++) {

                    ChatDashboardModel.DashboardMessage model = dataList.get(i);

                    ChatDashboardModel.DashboardMessage data = new ChatDashboardModel.DashboardMessage(
                            model.getDoctor_id(), model.getPatient_id(), model.getSend_by(),
                            model.getMessage(), model.getDate(), model.getTime(), model.getChat_image(), model.getChat_video());

                    chatList.add(data);
//                    Glob.dialog.dismiss();
                }


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

    public void sendImageInChat(String token, String user_id, String doctor_id, String msg_type, File image) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        RequestBody requestBody_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody requestBody_user_id = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody requestBody_doctor_id = RequestBody.create(MediaType.parse("multipart/form-data"), doctor_id);
        RequestBody requestBody_msg_type = RequestBody.create(MediaType.parse("multipart/form-data"), msg_type);

        MultipartBody.Part requestBody_profile_image = null;
        RequestBody requestBody_req_img = RequestBody.create(MediaType.parse("multipart/form-data"), image);
        requestBody_profile_image = MultipartBody.Part.createFormData("image", img_file.getName(), requestBody_req_img);


        call.sendImageInChat(requestBody_token, requestBody_user_id, requestBody_doctor_id, requestBody_msg_type, requestBody_profile_image).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                getChatMessage(Glob.Token, Glob.user_id, doctor_id);
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setData() {

        chatDashboardAdapter = new ChatDashboardAdapter(chatList, getApplicationContext(), new ChatDashboardAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onVideoSendView(int position) {


                String videoUrl = chatList.get(position).getChat_video();

                Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
                intent.putExtra("videoUrl", videoUrl);
                startActivity(intent);
            }

            @Override
            public void onVideoReceivedView(int position) {

                String videoUrl = chatList.get(position).getChat_video();

                Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
                intent.putExtra("videoUrl", videoUrl);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        chatRecycler.setLayoutManager(mLayoutManager);
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

                getChatMessageDummy(Glob.Token, Glob.user_id, doctor_id);
                int s = chatList.size();
                if (chatListSize == 0) {
                    getChatMessage(Glob.Token, Glob.user_id, doctor_id);
                        chatListSize = chatList.size() + 1;
                        Log.e(TAG, "run: " + chatListSize + "orr" + chatList.size());
                }
                if (chatListSize == s){

                    getChatMessage(Glob.Token, Glob.user_id, doctor_id);
                    chatListSize = chatList.size() + 1;
                    Log.e(TAG, "run: " + chatListSize + "orr" + chatList.size());
                }

                Log.e(TAG, "run: " + s);

                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLaunchCamera();
            } else {
                Toast.makeText(getApplicationContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MY_Gallery_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMediaContent();
            } else {
                Toast.makeText(getApplicationContext(), "Don't have permission to access file location", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onLaunchCamera() {


        // create Intent to take a picture and return control to the calling application

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Create a File reference for future access
            photoFile = getPhotoFileUri(photoFileName);
            img_url = Uri.fromFile(photoFile);

            Uri fileProvider = FileProvider.getUriForFile(getApplicationContext(), "com.in.patient.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {

        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "APP_TAG");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("APP_TAG", "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    public void openMediaContent() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        photoFile = getPhotoFileUri(photoFileName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri tempFileUri = FileProvider.getUriForFile(getApplicationContext(),
                    "com.in.patient.provider", // As defined in Manifest
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        } else {
            Uri tempFileUri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        }

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }

    public Bitmap getBitmap(final Uri selectedimg) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        AssetFileDescriptor fileDescriptor = null;

        fileDescriptor =
                getContentResolver().openAssetFileDescriptor(selectedimg, "r");
        Bitmap bitmap
                = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);

        options.inSampleSize = calculateInSampleSize(options, 1024, 1024);
        options.inJustDecodeBounds = false;

        Bitmap original
                = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);
        System.gc();
        return original;
    }

    public int calculateInSampleSize(@NonNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if ((height > reqHeight) && (width > reqWidth)) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            inSampleSize++;
        }
        return inSampleSize;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        if (resultCode == Activity.RESULT_OK) {
            try {
                switch (requestCode) {
                    case 1034:

                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                                Date());
                        img_file = new File(getCacheDir(), "IMG_" + timeStamp + ".jpg");

                        bitmap = null;

                        try {
                            if (img_url != null) {
                                bitmap = getBitmap(img_url);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (bitmap != null) {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                            byte[] bitmapdata = bos.toByteArray();

                            try {
                                FileOutputStream fos = new FileOutputStream(img_file);
                                fos.write(bitmapdata);
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("img__file", "onClick: " + img_file);


                            sendImageInChat(Glob.Token, Glob.user_id, doctor_id, "i", img_file);

                        }
                        break;
                    case 2:
                        if (data.getData() != null) {
//                            post();
                            bitmap = getBitmap(data.getData());
                            img_url = data.getData();

                            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                                    Date());
                            img_file = new File(getApplicationContext().getCacheDir(), "IMG_" + timeStamp + ".jpg");

                            bitmap = null;

                            try {
                                if (img_url != null) {
                                    bitmap = getBitmap(img_url);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (bitmap != null) {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                                byte[] bitmapdata = bos.toByteArray();

                                try {
                                    FileOutputStream fos = new FileOutputStream(img_file);
                                    fos.write(bitmapdata);
                                    fos.flush();
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.e("img_file", "onClick: " + img_file);

                                sendImageInChat(Glob.Token, Glob.user_id, doctor_id, "i", img_file);
                            }

                        }
                        break;

                    case 10:

                        if (resultCode == RESULT_OK) {
                            uri = data.getData();
                            if (data.getClipData() != null) {
                                int count = data.getClipData().getItemCount();
                                int currentItem = 0;
                                while (currentItem < count) {
                                    Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                                    //do something with the image (save it to some directory or whatever you need to do with it here)
                                    currentItem = currentItem + 1;

                                    uri = imageUri;
                                    Log.e("onActivityResult", "onActivityResult: " + uri);
                                    uploadFile();
                                }
                            } else if (data.getData() != null) {

                                uri = data.getData();
                                //do something with the image (save it to some directory or whatever you need to do with it here)
                                Log.e("onActivityResoult", "onActivityResult: " + uri);
                                uploadFile();
                            }
                        }
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void uploadFile() {

        try {
            if (Build.VERSION.SDK_INT >= 26) {

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel = new NotificationChannel("TestChannel", "TestApp Channel", NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
            }
            UploadServiceConfig.initialize(getApplication(), "TestChannel", BuildConfig.DEBUG);
            MultipartUploadRequest uploadRequest = new MultipartUploadRequest(this, "http://ciam.notionprojects.tech/api/patient/add_patient_chat.php")
                    .setMethod("POST")
                    .addFileToUpload(uri.toString(), "video")
                    .addParameter("token", Token)
                    .addParameter("user_id", user_id)
                    .addParameter("doctor_id", doctor_id)
                    .addParameter("msg_type", "v");
            uploadRequest.startUpload();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
