package com.in.patient.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.in.patient.fragment.Deposit;
import com.in.patient.fragment.Redemption;
import com.in.patient.fragment.TransactionHistory;


public class MyWalletAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;


    public MyWalletAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TransactionHistory transactionHistory = new TransactionHistory();
                return transactionHistory;

            case 1:
                Redemption redemption = new Redemption();
                return redemption;
            case 2:
                Deposit deposit = new Deposit();
                return deposit;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
