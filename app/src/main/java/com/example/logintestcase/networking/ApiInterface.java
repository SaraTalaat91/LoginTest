package com.example.logintestcase.networking;

import com.example.logintestcase.models.LoginModel;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @POST("api/LoginUser")
    @Multipart
    Call<LoginModel> login(@Part("login") String loginEmail, @Part("password") String password);
}