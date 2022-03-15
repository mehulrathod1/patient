package com.in.patient.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.in.patient.fragment.CompletedAppointment;
import com.in.patient.fragment.UpcomingAppointment;

public class MyAppointmentTabAdapter extends FragmentPagerAdapter {


    Context context;
    int totalTabs;

    public MyAppointmentTabAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CompletedAppointment completedAppointment = new CompletedAppointment();
                return completedAppointment;


            case 1:

                UpcomingAppointment upcommingAppointment = new UpcomingAppointment();
                return upcommingAppointment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
