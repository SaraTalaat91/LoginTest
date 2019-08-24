package com.example.logintestcase.activities;

import android.os.Build;
import android.os.Bundle;

import com.example.logintestcase.R;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @OnClick(R.id.login_button)
    void loginUser(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        validateLogin(email, password);
    }

    private void validateLogin(String email, String password) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

        }else{
            Toast.makeText(this, R.string.empty_fields_toast, Toast.LENGTH_SHORT).show();
        }
    }

}
