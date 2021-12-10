package com.in.patient.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.in.patient.fragment.ProfileSettingLifestyle;
import com.in.patient.fragment.ProfileSettingMedical;
import com.in.patient.fragment.ProfileSettingPersonal;

public class ProfileSettingAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;


    public ProfileSettingAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProfileSettingPersonal profileSettingPersonal = new ProfileSettingPersonal();
                return profileSettingPersonal;

            case 1:
                ProfileSettingMedical profileSettingMedical = new ProfileSettingMedical();
                return profileSettingMedical;

            case 2:

                ProfileSettingLifestyle profileSettingLifestyle = new ProfileSettingLifestyle();
                return profileSettingLifestyle;


            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
