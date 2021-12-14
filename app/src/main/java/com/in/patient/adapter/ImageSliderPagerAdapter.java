package com.in.patient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.in.patient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageSliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<String> image_arraylist;
    Click click;


    public interface Click {
        void itemClick(int position);
    }

    public ImageSliderPagerAdapter(Activity activity, ArrayList<String> image_arraylist, Click click) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.click = click;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.image_slider_item, container, false);
        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);

        im_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.itemClick(position);
            }
        });
        Picasso.get()
                .load(image_arraylist.get(position))
                .placeholder(R.drawable.ic_review_star_24) // optional
                .error(R.mipmap.ic_launcher)         // optional
                .into(im_slider);


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
