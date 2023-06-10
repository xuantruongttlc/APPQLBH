package com.example.thulai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handlerm= new Handler();
        handlerm.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextACtivty();
            }
        },2000);
    }

    private void nextACtivty() {
       FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
       if(user == null){
           //Chua login
           Intent intent = new Intent(this, LoginActivity.class);
           startActivity(intent);
           finish();
       }
       else {
           // Da login
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
           finish();
       }
    }
}