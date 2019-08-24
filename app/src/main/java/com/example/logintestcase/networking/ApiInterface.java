package com.example.logintestcase.networking;

import com.example.logintestcase.models.LoginModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("api/LoginUser")
    Call<LoginModel> login(@Part("login") RequestBody loginEmail, @Part("password") RequestBody password);
}