package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.helper.SessionManager;


public class SplashScreenActivity extends AppCompatActivity {


SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        session = new SessionManager(this);


        Handler penahan = new Handler();
        penahan.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(session.isLogin()) {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                } else {
                    Intent niat = new Intent(SplashScreenActivity.this, DaftarLoginActivity.class );
                    startActivity(niat);
                    finish();
                }
            }
        },2500);


    }
}
