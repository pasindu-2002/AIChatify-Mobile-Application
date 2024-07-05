package com.example.chatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOrSignupActivity extends AppCompatActivity {

    Button btnLogin,btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);

        btnLogin =findViewById(R.id.btnMainLogin);
        btnSignup = findViewById(R.id.btnMainSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOrSignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOrSignupActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}