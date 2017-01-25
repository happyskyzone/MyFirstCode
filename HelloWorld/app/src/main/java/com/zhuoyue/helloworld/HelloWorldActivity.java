package com.zhuoyue.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class HelloWorldActivity extends AppCompatActivity {

    //快捷输入logt生成TAG
    private static final String TAG = "HelloWorldActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        Log.i(TAG, "onCreate run");
    }
}
