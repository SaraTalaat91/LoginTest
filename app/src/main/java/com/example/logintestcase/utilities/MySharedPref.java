package com.example.logintestcase.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.logintestcase.models.LoginModel;
import com.google.gson.Gson;

public class MySharedPref {

    public static final String LOGIN_MODEL = "login_model";
    public static final String SHARED_PREF = "shared_pref";

    public static void setLoginModel(Context context, LoginModel loginModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String loginModelJson = gson.toJson(loginModel);
        editor.putString(LOGIN_MODEL, loginModelJson);
    }

    public static LoginModel getLoginModel(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        String loginModelJson = sharedPreferences.getString(LOGIN_MODEL, null);
        Gson gson = new Gson();
        return gson.fromJson(loginModelJson, LoginModel.class);
    }


}
