package com.zhuoyue.activitycontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnSecAty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //快捷方式启动SecondActivity并传入参数
                SecondActivity.actionStart(MainActivity.this,"参数1","参数2");
            }
        });
    }
}
