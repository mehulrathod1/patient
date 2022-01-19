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
import com.in.patient.model.SliderModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<SliderModel> image_arraylist;
    Click click;


    public interface Click {
        void itemClick(int position);
    }

    public SliderPagerAdapter(Activity activity, ArrayList<SliderModel> image_arraylist, Click click) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.click = click;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
        TextView category_name = view.findViewById(R.id.category_name);
        TextView consultNow = view.findViewById(R.id.consultNow);
        TextView short_description = view.findViewById(R.id.short_description);


        SliderModel model = image_arraylist.get(position);

        category_name.setText(model.getCategory_name());
        consultNow.setText(model.getConsultNow());
        short_description.setText(model.getShort_description());
        im_slider.setImageResource(model.getIm_slider());

        consultNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.itemClick(position);
            }
        });



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
