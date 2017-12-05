package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.kelique.chatlatian.R;

public class WebEcashActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_ecash);

        mWebView = (WebView) findViewById(R.id.webEcash);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.mandiriecash.com/agent/do/login");
    }
}
