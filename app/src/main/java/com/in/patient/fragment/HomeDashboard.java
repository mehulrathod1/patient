package com.in.patient.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.activity.LabTest;
import com.in.patient.activity.Search;
import com.in.patient.activity.VideoCallScreen;
import com.in.patient.adapter.FindDoctorAdapter;
import com.in.patient.adapter.HealthCareAdapter;
import com.in.patient.adapter.SliderPagerAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CareAndCheckupModel;
import com.in.patient.model.CommonModel;
import com.in.patient.model.DoctorConsultantSecondModel;
import com.in.patient.model.FindDoctorModel;
import com.in.patient.model.SliderModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDashboard extends Fragment {


    View view;
    Fragment fragment;
    TextView viewAllDoctor, viewAllServices, viewAllCheckup, search;
    ImageView search_icon;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    Spinner spn_state_name;
    ArrayAdapter<String> cityNameAdapter;
    List<String> cityNameList = new ArrayList<>();

    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<SliderModel> slider_image_list;
    private TextView[] dots;
    int page_position = 0;
    RecyclerView recyclerView, healthCheckupRecycler, healthCareRecycler;
    HealthCareAdapter healthCareAdapter;
    List<CareAndCheckupModel> careList = new ArrayList<>();
    List<CareAndCheckupModel> healthList = new ArrayList<>();

    FindDoctorAdapter adapter;
    List<FindDoctorModel.DoctorSpecialities> list = new ArrayList<>();

    LinearLayout doctorConsultant, homeCare, labTest, medicines, healthProduct,search_layout;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);

        init();
        healthCareData();
        healthCheckupData();
        addBottomDots(0);
        getDoctorSpecialist(Glob.Token, Glob.user_id);

        return view;
    }

    @SuppressLint("ResourceAsColor")
    public void init() {

        recyclerView = view.findViewById(R.id.findDoctor);
        healthCareRecycler = view.findViewById(R.id.healthCareRecycler);
        healthCheckupRecycler = view.findViewById(R.id.healthCheckupRecycler);

        doctorConsultant = view.findViewById(R.id.doctorConsultant);
        homeCare = view.findViewById(R.id.homeCare);
        labTest = view.findViewById(R.id.labTest);
        medicines = view.findViewById(R.id.medicines);
        healthProduct = view.findViewById(R.id.healthProduct);
        viewAllDoctor = view.findViewById(R.id.viewAllDoctor);
        viewAllServices = view.findViewById(R.id.viewAllServices);
        viewAllCheckup = view.findViewById(R.id.viewAllCheckup);
        search_layout = view.findViewById(R.id.search_layout);


        search_icon = view.findViewById(R.id.search_icon);
        search = view.findViewById(R.id.search);

        spn_state_name = view.findViewById(R.id.state_name);

        vp_slider = view.findViewById(R.id.vp_slider);
        ll_dots = view.findViewById(R.id.ll_dots);


        cityNameList.add("Gujarat");
        cityNameList.add("Maharashtra");
        cityNameList.add("Rajasthan");

        cityNameAdapter = new ArrayAdapter<String>(getContext(), R.layout.profile_spinner_text, cityNameList);
        cityNameAdapter.setDropDownViewResource(R.layout.dropdown_item);
        spn_state_name.setAdapter(cityNameAdapter);

        viewAllDoctor.setText(Html.fromHtml("<u>View All</u>"));
        viewAllServices.setText(Html.fromHtml("<u>View All</u>"));
        viewAllCheckup.setText(Html.fromHtml("<u>View All</u>"));
        slider_image_list = new ArrayList<>();


        sliderData();

//        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list, new SliderPagerAdapter.Click() {
//            @Override
//            public void itemClick(int position) {
//
//                Intent intent = new Intent(getActivity(), DoctorProfile.class);
//                startActivity(intent);
//
//                Toast.makeText(getContext(), ""+slider_image_list.get(position), Toast.LENGTH_SHORT).show();
////                sendNotification("eGMGXI7USQy0AUIVVwqoKj:APA91bHXuyjdBttPkKBlnSeXTnlzFXIQM0QTMs1TO0p6ZbeJ7tVp0Lo7M1-PgbFzM1keg72w6Dzw0X03uQz1CbTMPcdWyshgbth9ce8-2bYj7ayTaurrtQZ_gQOZ5U04nXXzuywFtgAW");
////                Intent intent = new Intent(getContext(), VideoCallScreen.class);
////                startActivity(intent);
//
//            }
//        });

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                addBottomDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };
        new Timer().schedule(new TimerTask() {


            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);


//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Search.class);
//                intent.putExtra("flag", "MainActivity");
//                startActivity(intent);
//            }
//        });
//
//        search_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Search.class);
//                intent.putExtra("flag", "MainActivity");
//                startActivity(intent);
//            }
//        });

        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("Flag", "MainActivity");
                startActivity(intent);
            }
        });
        doctorConsultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DoctorConsultSecond();
                Bundle spe_id = new Bundle();
                spe_id.putString("specialist_id", "0");
                fragment.setArguments(spe_id);
                loadFragment(fragment);
            }
        });
        homeCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CareServices();
                loadFragment(fragment);
            }
        });
        labTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Lab();
                loadFragment(fragment);
            }
        });
        medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Medicines();
                loadFragment(fragment);
            }
        });
        healthProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Product();
                loadFragment(fragment);
            }
        });

        viewAllDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new DoctorConsultSecond();
                Bundle spe_id = new Bundle();
                spe_id.putString("specialist_id", "0");
                fragment.setArguments(spe_id);
                loadFragment(fragment);

            }
        });

        viewAllServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CareServices();
                loadFragment(fragment);
            }
        });
        viewAllCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CareServices();
                loadFragment(fragment);
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];
        ll_dots.removeAllViews();
        if (getActivity() != null) {
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(getActivity());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(Color.parseColor("#EFEFEF"));
                ll_dots.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(Color.parseColor("#233E8B"));

        }
    }


    public void getDoctorSpecialist(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);


        call.getDoctorSpecialist(token, user_id).enqueue(new Callback<FindDoctorModel>() {
            @Override
            public void onResponse(Call<FindDoctorModel> call, Response<FindDoctorModel> response) {

                FindDoctorModel findDoctorModel = response.body();

                List<FindDoctorModel.DoctorSpecialities> DataList = findDoctorModel.getDoctorSpecialitiesList();

                for (int i = 0; i < DataList.size(); i++) {

                    FindDoctorModel.DoctorSpecialities model = DataList.get(i);

                    FindDoctorModel.DoctorSpecialities data = new FindDoctorModel.DoctorSpecialities(
                            model.getSpecialistId(), model.getSpecialistName(),
                            model.getSpecialistImg()
                    );

                    list.add(data);

                }
                DoctorSpecialistData();

            }

            @Override
            public void onFailure(Call<FindDoctorModel> call, Throwable t) {

            }
        });
    }


    public void DoctorSpecialistData() {


        adapter = new FindDoctorAdapter(list, getContext(), new FindDoctorAdapter.Click() {
            @Override
            public void onButtonClick(int position) {

                String specialist_id = list.get(position).getSpecialistId();
                Log.e("specialist_id", "onButtonClick: " + specialist_id);

                Fragment fragment = new DoctorConsultSecond();
                Bundle spe_id = new Bundle();
                spe_id.putString("specialist_id", specialist_id);
                fragment.setArguments(spe_id);
                loadFragment(fragment);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void healthCareData() {

        CareAndCheckupModel model = new CareAndCheckupModel("", "Covid Care");
        CareAndCheckupModel model1 = new CareAndCheckupModel("", "Physiotherapy");
        CareAndCheckupModel model2 = new CareAndCheckupModel("", "Medical Equipment");
        CareAndCheckupModel model3 = new CareAndCheckupModel("", "Gynaecologist");
        careList.add(model);
        careList.add(model1);
        careList.add(model2);
        careList.add(model3);


        healthCareAdapter = new HealthCareAdapter(careList, getContext(), new HealthCareAdapter.Click() {
            @Override
            public void onClick(int position) {

                Fragment fragment = new CareServices();
                loadFragment(fragment);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        healthCareRecycler.setLayoutManager(mLayoutManager);
        healthCareRecycler.setAdapter(healthCareAdapter);
    }

    public void healthCheckupData() {

        CareAndCheckupModel model = new CareAndCheckupModel("", "Health Checkup 1");
        CareAndCheckupModel model1 = new CareAndCheckupModel("", "Health Checkup 2");
        CareAndCheckupModel model2 = new CareAndCheckupModel("", "Health Checkup 3");
        CareAndCheckupModel model3 = new CareAndCheckupModel("", "Health Checkup 4");
        healthList.add(model);
        healthList.add(model1);
        healthList.add(model2);
        healthList.add(model3);


        healthCareAdapter = new HealthCareAdapter(healthList, getContext(), new HealthCareAdapter.Click() {
            @Override
            public void onClick(int position) {
                Fragment fragment = new CareServices();
                loadFragment(fragment);


            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        healthCheckupRecycler.setLayoutManager(mLayoutManager);
        healthCheckupRecycler.setAdapter(healthCareAdapter);
    }

    public void sliderData() {

        SliderModel model = new SliderModel("DoctorConsultant", "India's largest home health care company", "Consultation", R.drawable.rectangle_1);
        SliderModel model1 = new SliderModel("Home Care Services", "India's largest home health care company", "Services", R.drawable.rectangle_2);
        SliderModel model2 = new SliderModel("Lab Test", "India's largest home health care company", "Lab Test", R.drawable.rectangle_3);
        SliderModel model3 = new SliderModel("Medicine", "India's largest home health care company", "Medicine", R.drawable.rectangle_2);
        SliderModel model4 = new SliderModel("Care & Product", "India's largest home health care company", "Product", R.drawable.rectangle_4);
        SliderModel model5 = new SliderModel("Stress buster zone", "India's largest home health care company", "Stress buster", R.drawable.rectangle5);
        SliderModel model6 = new SliderModel("Ask Question", "India's largest home health care company", "Ask question", R.drawable.rectangle_4);
        SliderModel model7 = new SliderModel("Knowledge Forum", "India's largest home health care company", "Knowledge Forum", R.drawable.rectangle_6);


        slider_image_list.add(model);
        slider_image_list.add(model1);
        slider_image_list.add(model2);
        slider_image_list.add(model3);
        slider_image_list.add(model4);
        slider_image_list.add(model5);
        slider_image_list.add(model6);
        slider_image_list.add(model7);


        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list, new SliderPagerAdapter.Click() {
            @Override
            public void itemClick(int position) {

                //Toast.makeText(getContext(), "" + slider_image_list.get(position).getConsultNow(), Toast.LENGTH_SHORT).show();
                String button_text = slider_image_list.get(position).getConsultNow();

                if (button_text.equals("Consultation")) {

                    Fragment fragment = new DoctorConsultSecond();
                    Bundle spe_id = new Bundle();
                    spe_id.putString("specialist_id", "0");
                    fragment.setArguments(spe_id);
                    loadFragment(fragment);
                } else if (button_text.equals("Services")) {
                    Fragment fragment = new CareServices();
                    loadFragment(fragment);

                } else if (button_text.equals("Lab Test")) {
                    Fragment fragment = new Lab();
                    loadFragment(fragment);

                } else if (button_text.equals("Medicine")) {
                    Fragment fragment = new Medicines();
                    loadFragment(fragment);

                } else if (button_text.equals("Product")) {
                    Fragment fragment = new Product();
                    loadFragment(fragment);

                } else if (button_text.equals("Stress buster")) {

                } else if (button_text.equals("Ask question")) {

                } else if (button_text.equals("Knowledge Forum")) {

                }


            }
        });
        vp_slider.setAdapter(sliderPagerAdapter);

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    private void sendNotification(final String regToken) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    JSONObject json = new JSONObject();
//                    JSONObject dataJson = new JSONObject();
//                    dataJson.put("body", "");
//                    dataJson.put("title", "dummy notification");
//                    json.put("notification", dataJson);
//                    json.put("to", regToken);
//                    RequestBody body = RequestBody.create(JSON, json.toString());
//                    Request request = new Request.Builder()
//                            .header("Authorization", "key=" + "AAAAEhxA8sc:APA91bGzKFx7gAT8wnp2rCjvhz12SZ-nGhg6HF3dffOhfOBpKAxYWvRpfkoRmWSnZd2_W1-ez8gizm1di1BAjmA-HBvD5QnVoPTEPwNTmGBR1NSONAcLV36OOZ_hlhMYMBDqVCEesOOQ")
//                            .url("https://fcm.googleapis.com/fcm/send")
//                            .post(body)
//                            .build();
//                    okhttp3.Response response = client.newCall(request).execute();
//                    String finalResponse = response.body().string();
//                    Log.e("doInBackground", "doInBackground: " + finalResponse);
//                } catch (Exception e) {
//                    //Log.d(TAG,e+"");
//                }
//                return null;
//            }
//        }.execute();
//
//    }


//    public void sendNotification(String token, String user_id, String message) {
//
//        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
//
//
//        call.sendNotification(token, user_id, message).enqueue(new Callback<CommonModel>() {
//            @Override
//            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
//
//                CommonModel model = response.body();
//
//                Log.e("getMessage", "onResponse: " + model.getMessage());
//            }
//
//            @Override
//            public void onFailure(Call<CommonModel> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
