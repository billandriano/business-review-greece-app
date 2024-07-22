package com.my.busrevapp;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ImageViewer extends AppCompatActivity {
    private WebView webview;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageviewer);
        webview = (WebView) findViewById(R.id.webviewimage);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setBackgroundColor(Color.parseColor("#000000"));
        webview.loadUrl(getIntent().getStringExtra("imageurl"));
    }
}
