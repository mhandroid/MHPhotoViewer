package com.mh.android.photoviewer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Utility class for application utility method
 * Created by @author Mubarak Hussain.
 */
public class Utils {
    public static final String ALBUM_URL = "ALBUM_URL";
    public static final String ALBUM_TITLE = "ALBUM_TITLE";

    /**
     * Method to check network is available or not
     *
     * @param context
     * @return
     */
    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
