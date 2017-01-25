package com.zhuoyue.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //接收来自FirstActitity的数据,对应FirstActicity中的发送数据intent
//        Intent intent=getIntent();
//        String data_extra=intent.getStringExtra("data_extra");
//        Log.d(TAG, data_extra);

        //点击按钮button2关闭SecondActivity并且将数据传回FirstActivity
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SecondActivity.this,FirstActivity.class);
                i.putExtra("data_return","Hello FirstActicity");
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }

    /**
     * 重写按下返回键事件，实现关闭SecondActicity并将数据返回值FirstActivity
     */
    @Override
    public void onBackPressed() {
        Intent i=new Intent(SecondActivity.this,FirstActivity.class);
        i.putExtra("data_return","Hello FirstActicity");
        setResult(RESULT_OK,i);
        finish();
    }
}
