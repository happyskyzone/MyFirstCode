package com.zhuoyue.activitycontroller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends BaseActivity {

    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //接收来自其他Activity的数据
        Intent intent = getIntent();
        String data1=intent.getStringExtra("param1");
        String data2=intent.getStringExtra("param2");
        Log.d(TAG, "data1:"+data1+",data2:"+data2);
    }

    //此方法为了方便从其他Activity跳转至此Activity并且传入参数
    public static void actionStart(Context context,String data1,String data2){
        Intent intent = new Intent(context,SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }
}
