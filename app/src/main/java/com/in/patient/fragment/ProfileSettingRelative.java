package com.in.patient.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.patient.R;

import org.w3c.dom.Text;

public class ProfileSettingRelative extends Fragment {
    TextView hello;
    View view;

    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    ImageView dialogClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_setting_relative, container, false);
        init();
        return view;
    }

    public void init() {

        hello = view.findViewById(R.id.hello);


        alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.add_relative_dialog, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();

        dialogClose = dialogLayout.findViewById(R.id.dialogClose);

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.show();
            }
        });
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }
}