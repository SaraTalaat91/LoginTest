package com.example.logintestcase.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

public class Connectivity {
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}