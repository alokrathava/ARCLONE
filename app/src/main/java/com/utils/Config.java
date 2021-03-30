package com.utils;

import android.content.Context;
import android.widget.Toast;

public class Config {

    public static int user_id = -1;
    public static String base = "http://192.168.0.140/";
    public static String url = base + "ar_panel/Api/api/";
    public static String imageUrl = base + "ar_panel/";

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


}
