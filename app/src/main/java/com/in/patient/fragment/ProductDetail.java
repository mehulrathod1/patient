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

import com.in.patient.R;
import com.in.patient.activity.DoctorProfile;
import com.in.patient.adapter.ImageSliderPagerAdapter;
import com.in.patient.adapter.SliderPagerAdapter;

import java.util.ArrayList;

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
        addBottomDots(0);
        return view;
    }

    public void init() {
        vp_slider = view.findViewById(R.id.vp_slider);
        ll_dots = view.findViewById(R.id.ll_dots);

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

}
