package com.zhuoyue.uilayouttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected Button btn_showPercentFrameLayout;
    protected Button btn_showLinerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_showPercentFrameLayout = (Button) findViewById(R.id.btn_showPercentFrameLayout);
        btn_showLinerlayout = (Button) findViewById(R.id.btn_showLinerlayout);

        btn_showLinerlayout.setOnClickListener(this);
        btn_showPercentFrameLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_showLinerlayout:
                Intent i1=new Intent(MainActivity.this,LinerLayoutActivity.class);
                startActivity(i1);
                break;
            case R.id.btn_showFrameLayout:
                break;
            case R.id.btn_showRelativeLayout:
                break;
            case R.id.btn_showPercentFrameLayout:
                Intent intent = new Intent(MainActivity.this,PercentFrameLayoutActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}
