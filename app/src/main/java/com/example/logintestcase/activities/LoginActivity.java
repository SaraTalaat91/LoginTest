package com.example.logintestcase.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.logintestcase.R;
import com.example.logintestcase.models.LoginModel;
import com.example.logintestcase.utilities.Connectivity;
import com.example.logintestcase.utilities.MySharedPref;
import com.example.logintestcase.viewmodels.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;

    private LoginViewModel mLoginViewModel;
    private LiveData<LoginModel> mLoginModel;
    private LiveData<String> mLoginError;
    private LiveData<Boolean> mIsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkLoginStatus();
        initViews();
        initViewModel();
        initObservers();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void checkLoginStatus() {
        if (MySharedPref.getLoginModel(getApplicationContext()) != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void initViews() {
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        progressBar = findViewById(R.id.progress_bar);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void initViewModel() {
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginModel = mLoginViewModel.getLoginModel();
        mLoginError = mLoginViewModel.getLoginError();
        mIsLoading = mLoginViewModel.getLoadingState();
    }

    private void initObservers() {
        mLoginModel.observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                mLoginViewModel.finishedLoading();
                MySharedPref.setLoginModel(getApplicationContext(), loginModel);
                Toast.makeText(LoginActivity.this, R.string.login_successful_toast, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mLoginError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mLoginViewModel.finishedLoading();
                Toast.makeText(LoginActivity.this, R.string.incorrect_email_password_toast, Toast.LENGTH_LONG).show();
            }
        });

        mIsLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
            }
        });
    }

    void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (Connectivity.isConnected(getApplicationContext())) {
            validateLogin(email, password);
        } else {
            showNoConnectionSnack();
        }
    }

    private void showNoConnectionSnack() {
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_internet_connection_snack), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.dismiss_snack, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        }).show();
    }

    private void validateLogin(String email, String password) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mLoginViewModel.loginAccount(email, password);
        } else {
            Toast.makeText(this, R.string.empty_fields_toast, Toast.LENGTH_SHORT).show();
        }
    }

}
