package com.example.logintestcase.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logintestcase.models.LoginModel;
import com.example.logintestcase.networking.ApiClient;
import com.example.logintestcase.networking.ApiInterface;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginModel> mLoginModel = new MutableLiveData<>();
    private MutableLiveData<String> mLoginError = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private ApiInterface mApiInterface;

    public LoginViewModel() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void loginAccount(String email, String password) {
        isLoading.setValue(true);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);

        mApiInterface.login(emailBody, passwordBody).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mLoginModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                mLoginError.setValue(t.getMessage());
            }
        });
    }

    public LiveData<LoginModel> getLoginModel() {
        return mLoginModel;
    }

    public LiveData<String> getLoginError() {
        return mLoginError;
    }

    public LiveData<Boolean> getLoadingState(){
        return isLoading;
    }

    public void resetErrorMsg(){mLoginError.setValue("");}

    public void finishedLoading(){
        isLoading.setValue(false);
    }
}
