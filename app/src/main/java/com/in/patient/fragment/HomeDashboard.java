package com.in.patient.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.activity.LabTest;
import com.in.patient.adapter.FindDoctorAdapter;
import com.in.patient.adapter.HealthCareAdapter;
import com.in.patient.adapter.SliderPagerAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CareAndCheckupModel;
import com.in.patient.model.FindDoctorModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDashboard extends Fragment {


    View view;
    Fragment fragment;
    TextView viewAllDoctor, viewAllServices, viewAllCheckup;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;
    int page_position = 0;

    RecyclerView recyclerView, healthCheckupRecycler, healthCareRecycler;
    HealthCareAdapter healthCareAdapter;
    List<CareAndCheckupModel> careList = new ArrayList<>();
    List<CareAndCheckupModel> healthList = new ArrayList<>();

    FindDoctorAdapter adapter;
    List<FindDoctorModel.DoctorSpecialities> list = new ArrayList<>();

    LinearLayout doctorConsultant, homeCare, labTest, medicines, healthProduct;

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

        vp_slider = view.findViewById(R.id.vp_slider);
        ll_dots = view.findViewById(R.id.ll_dots);

        viewAllDoctor.setText(Html.fromHtml("<u>View All</u>"));
        viewAllServices.setText(Html.fromHtml("<u>View All</u>"));
        viewAllCheckup.setText(Html.fromHtml("<u>View All</u>"));

        slider_image_list = new ArrayList<>();
        slider_image_list.add("https://wallpaperaccess.com/full/297372.jpg");
        slider_image_list.add("https://www.teahub.io/photos/full/68-683520_beautiful-girl-wallpapers-hd.jpg");
        slider_image_list.add("https://wallpaperaccess.com/full/1198406.jpg");
        slider_image_list.add("https://www.wallpaperuse.com/wallp/50-509102_m.jpg");
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list, new SliderPagerAdapter.Click() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(getActivity(), DoctorProfile.class);
                startActivity(intent);
            }
        });
        vp_slider.setAdapter(sliderPagerAdapter);
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

//        final Handler handler = new Handler();
//
//        final Runnable update = new Runnable() {
//            public void run() {
//                if (page_position == slider_image_list.size()) {
//                    page_position = 0;
//                } else {
//                    page_position = page_position + 1;
//                }
//                vp_slider.setCurrentItem(page_position, true);
//            }
//        };
//
//        new Timer().schedule(new TimerTask() {
//
//
//            @Override
//            public void run() {
//                handler.post(update);
//            }
//        }, 100, 5000);


        doctorConsultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DoctorConsultSecond();
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
                Fragment fragment = new DoctorConsultant();
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

                Fragment fragment = new DoctorConsultSecond();
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

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public void sendNotification() {
    }

}
