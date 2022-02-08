package com.in.patient.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.adapter.DoctorConsultantSecondAdapter;
import com.in.patient.adapter.SliderPagerAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.DoctorConsultantSecondModel;
import com.in.patient.model.SliderModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Consultation extends Fragment {


    View view;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<SliderModel> slider_image_list = new ArrayList<>();
    private TextView[] dots;
    int page_position = 0;

    TextView viewAllDoctor;

    RecyclerView recyclerView;
    DoctorConsultantSecondAdapter adapter;
    List<DoctorConsultantSecondModel.ConsultantData> list = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_consultation, container, false);
        init();
        sliderData();
        getDoctor(Glob.Token, Glob.user_id);
        return view;

    }


    public void init() {


        Glob.progressDialog(getContext());

        vp_slider = view.findViewById(R.id.vp_slider);
        ll_dots = view.findViewById(R.id.ll_dots);
        viewAllDoctor = view.findViewById(R.id.viewAllDoctor);


        recyclerView = view.findViewById(R.id.recycler);

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

            }
        });

        vp_slider.setAdapter(sliderPagerAdapter);


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

    public void getDoctor(String token, String user_id) {

        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.getDoctor(token, user_id).enqueue(new Callback<DoctorConsultantSecondModel>() {
            @Override
            public void onResponse(Call<DoctorConsultantSecondModel> call, Response<DoctorConsultantSecondModel> response) {

                DoctorConsultantSecondModel doctorConsultantSecondModel = response.body();

                List<DoctorConsultantSecondModel.ConsultantData> DataList = doctorConsultantSecondModel.getConsultantDataList();

                for (int i = 0; i < DataList.size(); i++) {

                    DoctorConsultantSecondModel.ConsultantData model = DataList.get(i);

                    DoctorConsultantSecondModel.ConsultantData data = new DoctorConsultantSecondModel.ConsultantData(
                            model.getUser_id(), model.getFirst_name(), model.getLast_name(),
                            model.getSpecialist(), model.getExperience() + " yrs of exp overall", model.getLocation(),
                            model.getAvailable(), model.getProfile_image()
                    );

                    list.add(data);
                }
                recyclerData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<DoctorConsultantSecondModel> call, Throwable t) {

            }
        });

    }

    public void recyclerData() {

        adapter = new DoctorConsultantSecondAdapter(list, getContext(), new DoctorConsultantSecondAdapter.Click() {
            @Override
            public void onItemClick(int position) {


//
                String doctorId = list.get(position).getUser_id();

                Intent intent = new Intent(getContext(), DoctorProfile.class);
                intent.putExtra("doctorId", doctorId);
//                intent.putExtra("FLag","DoctorConsultSecond");
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.firstFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}