package com.example.logintestcase.activities;

import android.os.Build;
import android.os.Bundle;

import com.example.logintestcase.R;
import com.example.logintestcase.viewmodels.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void initViews(){
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
    }

    void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        validateLogin(email, password);
    }

    private void validateLogin(String email, String password) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mLoginViewModel.loginAccount(email, password);
        } else {
            Toast.makeText(this, R.string.empty_fields_toast, Toast.LENGTH_SHORT).show();
        }
    }

}
