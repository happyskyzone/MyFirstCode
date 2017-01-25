package com.zhuoyue.androidlifecycletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        //将存在bundle中的数据取出
        if(savedInstanceState!=null){
            String tempdata=savedInstanceState.getString("data_key");
            Log.d(TAG, "onCreate: "+tempdata);
        }

        findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent);
            }
        });
    }


    //以下测试Activity的生命周期
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    /*重写onSaveInstanceState将activity中需要暂存的数据存放起来，防止activity被系统回收后数据丢失，在onCreate中可以取出*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String temp_data="Something you just typed";
        outState.putString("data_key",temp_data);
    }
}
