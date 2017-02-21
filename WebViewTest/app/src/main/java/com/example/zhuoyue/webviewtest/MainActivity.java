package com.example.zhuoyue.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.wv_baidu);//授予网络权限
        webView.getSettings().setJavaScriptEnabled(true);//允许JS插件运行
        webView.setWebViewClient(new WebViewClient());//始终在webview中打开网页
        webView.loadUrl("http://www.baidu.com");
    }
}
