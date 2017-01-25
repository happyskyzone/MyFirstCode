package com.zhuoyue.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //第三个Activity在清单文件中设置了action为VIEW,可以相应data为http开头的Uri的intent
        //类型与浏览器一样，但是无法打开网页
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

    }
}
