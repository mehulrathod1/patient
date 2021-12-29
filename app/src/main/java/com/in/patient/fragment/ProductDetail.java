package com.in.patient.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.adapter.ImageSliderPagerAdapter;
import com.in.patient.adapter.SliderPagerAdapter;
import com.in.patient.globle.Glob;
import com.in.patient.model.CommonModel;
import com.in.patient.retrofit.Api;
import com.in.patient.retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail extends Fragment {

    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    ImageSliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;
    int page_position = 0;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);


        init();
//        addToCartProduct(Glob.Token, Glob.user_id, "1", "1");
        addBottomDots(0);
        return view;
    }

    public void init() {
        vp_slider = view.findViewById(R.id.vp_slider);
        ll_dots = view.findViewById(R.id.ll_dots);


        Glob.progressDialog(getContext());

        slider_image_list = new ArrayList<>();
        slider_image_list.add("https://wallpaperaccess.com/full/297372.jpg");
        slider_image_list.add("https://www.teahub.io/photos/full/68-683520_beautiful-girl-wallpapers-hd.jpg");
        slider_image_list.add("https://wallpaperaccess.com/full/1198406.jpg");
        slider_image_list.add("https://www.wallpaperuse.com/wallp/50-509102_m.jpg");


        sliderPagerAdapter = new ImageSliderPagerAdapter(getActivity(), slider_image_list, new ImageSliderPagerAdapter.Click() {
            @Override
            public void itemClick(int position) {

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


    public void addToCartProduct(String token, String user_id, String product_id, String product_qty) {
        Api call = RetrofitClient.getClient(Glob.Base_Url).create(Api.class);
        Glob.dialog.show();


        call.addToCartProduct(token, user_id, product_id, product_qty).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();

                Glob.dialog.dismiss();
                Toast.makeText(getContext(), "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }
}
