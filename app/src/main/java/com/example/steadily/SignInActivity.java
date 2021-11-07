package com.example.steadily;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {
    
    Activity mActivity;
    EditText mUserId;
    EditText mPassword;
    Button mLoginButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 상단 상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mActivity = this;

        mUserId = findViewById(R.id.user_id);
        mPassword = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login_button);
        
        // 로그인 버튼 누를 시 메인화면으로 넘어감
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                mActivity.startActivity(intent);
            }
        });
        
    }
}