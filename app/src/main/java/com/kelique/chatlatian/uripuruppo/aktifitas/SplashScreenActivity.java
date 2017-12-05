package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kelique.chatlatian.R;



public class SplashScreenActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler penahan = new Handler();
        penahan.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, DaftarLoginActivity.class));
                finish();
            }
        },2500);


    }
}
