package com.in.patient.globle;

import android.app.ProgressDialog;
import android.content.Context;

public class Glob {

    public static ProgressDialog dialog;
    public static String Base_Url = "http://ciam.notionprojects.tech/api/patient/";
    public static String Token = "123456789";
    public static String user_id = "1";


    public static void progressDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false); // set cancelable to false
        dialog.setMessage("Please Wait"); // set message

    }
}
