package com.zhuoyue.testbtnimpl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1= (Button) findViewById(R.id.btn1);
        Button btn2= (Button) findViewById(R.id.btn2);
        Button btn3= (Button) findViewById(R.id.btn3);

        //使用实现接口的方法来为按钮设置监听事件
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                //do something
                break;
            case R.id.btn2:
                //do something
                break;
            case R.id.btn3:
                //do something
                break;
            default:
        }
    }
}
