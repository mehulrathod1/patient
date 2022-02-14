package com.in.patient.fragment;

import static com.in.patient.globle.Glob.Token;
import static com.in.patient.globle.Glob.user_id;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.patient.R;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.model.ProfilePersonalModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingPersonal extends Fragment {

    View view;
    EditText edtFirstName, edtLastName, edtEmail, edtNumber, edtAge, edtGender, edtDob, edtBloodGroup, edtMaritalStatus, edtHeight, edtWeight, edtEmergencyNumber, edtAddress;

    TextView choosePhoto;

    Button submit;
    ImageView profileImage;

    File photoFile, img_file;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    public String photoFileName = "IMG_" + timeStamp + ".jpg";
    Uri img_url;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_Gallery_REQUEST_CODE = 101;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_setting_personal, container, false);

        init();

        getProfilePersonal(Token, user_id);
        return view;
    }

    private void init() {

        Glob.progressDialog(getContext());
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtNumber = view.findViewById(R.id.edtNumber);
        edtAge = view.findViewById(R.id.edtAge);
        edtGender = view.findViewById(R.id.edtGender);
        edtDob = view.findViewById(R.id.edtDob);
        edtBloodGroup = view.findViewById(R.id.edtBloodGroup);
        edtMaritalStatus = view.findViewById(R.id.edtMaritalStatus);
        edtHeight = view.findViewById(R.id.edtHeight);
        edtWeight = view.findViewById(R.id.edtWeight);
        edtEmergencyNumber = view.findViewById(R.id.edtEmergencyNumber);
        edtAddress = view.findViewById(R.id.edtAddress);

        profileImage = view.findViewById(R.id.profileImage);
        choosePhoto = view.findViewById(R.id.choosePhoto);

        submit = view.findViewById(R.id.Submit);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] options = {"Camera", "Add From Gallery", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (options[i].equals("Camera")) {
                            Toast.makeText(getActivity(), "camera", Toast.LENGTH_SHORT).show();
                            onLaunchCamera();
                        } else if (options[i].equals("Add From Gallery")) {
                            openMediaContent();
                            Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                        Date());
                img_file = new File(getActivity().getCacheDir(), "IMG_" + timeStamp + ".jpg");

                Bitmap bitmap = null;
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
                    Log.e("image_file", "onClick: " + img_file);
                    updatePaientPersonalWithImage(Token, user_id, edtFirstName.getText().toString(), edtLastName.getText().toString(),
                            edtEmail.getText().toString(), edtNumber.getText().toString(),
                            edtGender.getText().toString(), edtDob.getText().toString(),
                            edtBloodGroup.getText().toString(), edtMaritalStatus.getText().toString(),
                            edtHeight.getText().toString(), edtWeight.getText().toString(),
                            edtEmergencyNumber.getText().toString(), edtAddress.getText().toString(), img_file);
                } else {


                    updateProfilePersonal(Token, user_id, edtFirstName.getText().toString(), edtLastName.getText().toString(),
                            edtEmail.getText().toString(), edtNumber.getText().toString(),
                            edtGender.getText().toString(), edtDob.getText().toString(),
                            edtBloodGroup.getText().toString(), edtMaritalStatus.getText().toString(),
                            edtHeight.getText().toString(), edtWeight.getText().toString(),
                            edtEmergencyNumber.getText().toString(), edtAddress.getText().toString());

//
                }

//
//

            }
        });
    }

    public void getProfilePersonal(String token, String user_id) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getProfilePersonal(token, user_id).enqueue(new Callback<ProfilePersonalModel>() {
            @Override
            public void onResponse(Call<ProfilePersonalModel> call, Response<ProfilePersonalModel> response) {
                ProfilePersonalModel model = response.body();


                edtFirstName.setText(model.getData().getFirst_name());
                edtLastName.setText(model.getData().getLast_name());
                edtEmail.setText(model.getData().getEmail());
                edtNumber.setText(model.getData().getMobile_number());
                edtAge.setText(model.getData().getAge());
                edtGender.setText(model.getData().getGender());
                edtDob.setText(model.getData().getDOB());
                edtBloodGroup.setText(model.getData().getBlood_group());
                edtMaritalStatus.setText(model.getData().getMarital_status());
                edtHeight.setText(model.getData().getHeight());
                edtWeight.setText(model.getData().getWeight());
                edtEmergencyNumber.setText(model.getData().getEmergency_contact());
                edtAddress.setText(model.getData().getAddress());


                Glide.with(getContext())
                        .load(model.getData().getProfile())
                        .into(profileImage);

                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<ProfilePersonalModel> call, Throwable t) {

            }
        });

    }

    public void updateProfilePersonal(String token, String user_id, String first_name, String last_name,
                                      String email, String mobile_no, String gender, String dob, String blood_group,
                                      String marital_status, String height, String weight, String emergency_contact,
                                      String address) {


        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        call.updateProfilePersonal(token, user_id, first_name, last_name, email, mobile_no, gender, dob,
                blood_group, marital_status, height, weight, emergency_contact, address).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updatePaientPersonalWithImage(String token, String user_id, String first_name, String last_name,
                                              String email, String mobile_no, String gender, String dob, String blood_group,
                                              String marital_status, String height, String weight, String emergency_contact,
                                              String address, File Profile_image) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);

        RequestBody requestBody_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody requestBody_user_id = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody requestBody_first_name = RequestBody.create(MediaType.parse("multipart/form-data"), first_name);
        RequestBody requestBody_last_name = RequestBody.create(MediaType.parse("multipart/form-data"), last_name);
        RequestBody requestBody_email = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody requestBody_mobile_no = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_no);
        RequestBody requestBody_gender = RequestBody.create(MediaType.parse("multipart/form-data"), gender);
        RequestBody requestBody_dob = RequestBody.create(MediaType.parse("multipart/form-data"), dob);
        RequestBody requestBody_blood_group = RequestBody.create(MediaType.parse("multipart/form-data"), blood_group);
        RequestBody requestBody_marital_status = RequestBody.create(MediaType.parse("multipart/form-data"), marital_status);
        RequestBody requestBody_height = RequestBody.create(MediaType.parse("multipart/form-data"), height);
        RequestBody requestBody_weight = RequestBody.create(MediaType.parse("multipart/form-data"), weight);
        RequestBody requestBody_emergency_contact = RequestBody.create(MediaType.parse("multipart/form-data"), emergency_contact);
        RequestBody requestBody_address = RequestBody.create(MediaType.parse("multipart/form-data"), address);


        MultipartBody.Part requestBody_profile_image = null;
        RequestBody requestBody_req_img = RequestBody.create(MediaType.parse("multipart/form-data"), Profile_image);
        requestBody_profile_image = MultipartBody.Part.createFormData("image", img_file.getName(), requestBody_req_img);


        call.updatePatientPersonalWithImage(requestBody_token, requestBody_user_id, requestBody_first_name,
                requestBody_last_name, requestBody_email, requestBody_mobile_no,
                requestBody_gender, requestBody_dob, requestBody_blood_group, requestBody_marital_status,
                requestBody_height, requestBody_weight, requestBody_emergency_contact, requestBody_address,
                requestBody_profile_image).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLaunchCamera();
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MY_Gallery_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMediaContent();
            } else {
                Toast.makeText(getContext(), "Don't have permission to access file location", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onLaunchCamera() {


        // create Intent to take a picture and return control to the calling application

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Create a File reference for future access
            photoFile = getPhotoFileUri(photoFileName);
            img_url = Uri.fromFile(photoFile);

            Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.in.patient.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {

        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "APP_TAG");
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
            Uri tempFileUri = FileProvider.getUriForFile(getContext(),
                    "com.in.doctor.provider", // As defined in Manifest
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
                getActivity().getContentResolver().openAssetFileDescriptor(selectedimg, "r");
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
                        img_file = new File(getActivity().getCacheDir(), "IMG_" + timeStamp + ".jpg");

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

//                            updateDoctorPersonal("123456789", Glob.user_id, edtFirstName.getText().toString(),
//                                    edtLastName.getText().toString(), "1",
//                                    edtEducation.getText().toString(), edtLanguageSpoken.getText().toString(),
//                                    edtExperience.getText().toString(), edtAddress.getText().toString(), img_file);

                            Uri temporary_Image = Uri.fromFile(new File(String.valueOf(img_file)));
                            profileImage.setImageURI(temporary_Image);
                        }
                        break;
                    case 2:
                        if (data.getData() != null) {
//                            post();
                            bitmap = getBitmap(data.getData());
                            img_url = data.getData();

                            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                                    Date());
                            img_file = new File(getContext().getApplicationContext().getCacheDir(), "IMG_" + timeStamp + ".jpg");

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
                                Uri temporary_Image = Uri.fromFile(new File(String.valueOf(img_file)));
                                profileImage.setImageURI(temporary_Image);


//                                updateDoctorPersonal("123456789", Glob.user_id, edtFirstName.getText().toString(),
//                                        edtLastName.getText().toString(), "1",
//                                        edtEducation.getText().toString(), edtLanguageSpoken.getText().toString(),
//                                        edtExperience.getText().toString(), edtAddress.getText().toString(), img_file);
                            }

                        }
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }





}