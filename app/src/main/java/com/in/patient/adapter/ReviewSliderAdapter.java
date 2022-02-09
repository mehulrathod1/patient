package com.in.patient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.in.patient.R;
import com.in.patient.model.ReviewSliderModel;
import com.in.patient.model.SliderModel;

import java.util.ArrayList;

public class ReviewSliderAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<ReviewSliderModel> image_arraylist;
    Click click;

    public interface Click {
        void itemClick(int position);
    }

    public ReviewSliderAdapter(Activity activity, ArrayList<ReviewSliderModel> image_arraylist, Click click) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.click = click;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.my_reviw_item, container, false);


        ImageView im_slider = (ImageView) view.findViewById(R.id.profileImage);
        TextView user_name = view.findViewById(R.id.Name);
        TextView review_date = view.findViewById(R.id.Date);
        TextView review_text = view.findViewById(R.id.ReviewText);


        ReviewSliderModel model = image_arraylist.get(position);


        user_name.setText(model.getUser_name());
        review_text.setText(model.getReview_text());
        review_date.setText(model.getReview_date());
        im_slider.setImageResource(model.getIm_slider());

        container.addView(view);

        return view;
    }


    @Override
    public int getCount() {
        return image_arraylist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
