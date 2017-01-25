package com.zhuoyue.uiweighttest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= (EditText) findViewById(R.id.et);
        ImageView iv= (ImageView) findViewById(R.id.iv);

        //点击按钮将EditText中的内容以Toast形式展示
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr=et.getText().toString();
                Toast.makeText(MainActivity.this,inputStr,Toast.LENGTH_SHORT).show();
            }
        });

        //动态设置ImageView资源位置
        //iv.setImageResource();

        //设置点击按钮实现隐藏、出现进度条
        pb = (ProgressBar) findViewById(R.id.pb);
        findViewById(R.id.btn_show_pb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*设置点击按钮实现隐藏、出现进度条*/
                //if (pb.getVisibility()==View.VISIBLE)
                //    pb.setVisibility(View.INVISIBLE);
                //else
                //    pb.setVisibility(View.VISIBLE);

                /*设置点击pb增量为10*/
                int progerss = pb.getProgress();
                progerss=progerss+10;
                pb.setProgress(progerss);
            }
        });

        //点击按钮显示AlertDialog
        findViewById(R.id.btn_show_alertdiaglog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        //点击按钮显示ProgressDialog
        findViewById(R.id.btn_show_progressDiaglog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
            }
        });
    }

    private void showProgressDialog() {
        /*ProgressDialog相关属性的设置*/
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("This is a ProgressDialog");
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.show();
    }

    private void showAlertDialog() {
         /*提示框AlertDialog的相关属性设置*/
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("提示");
        dialog.setCancelable(false);
        dialog.setMessage("这是重要提示!");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"You click ok", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"You click cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
