package com.ashagunova.loftmoney_2.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.ashagunova.loftmoney_2.LoftApp;
import com.ashagunova.loftmoney_2.R;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configureViews();
        configureViewModel();

    }

    private void configureViews() {
        AppCompatButton loginEnterView = findViewById(R.id.loginEnterView);

        loginEnterView.setOnClickListener(v -> {
            loginViewModel.makeLogin(((LoftApp) getApplication()).authApi);
        });

    }

    private void configureViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.messageString.observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        });
        loginViewModel.authToken.observe(this, authToken -> {
            if (!TextUtils.isEmpty(authToken)) {

            }
        });
    }
}
