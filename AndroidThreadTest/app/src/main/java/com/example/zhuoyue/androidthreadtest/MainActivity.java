package com.example.zhuoyue.androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int UPDATE_TEXT = 1;
    private TextView textView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEXT:
                    textView.setText("You Channge the text");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnChangeText = (Button) findViewById(R.id.btn_change_text);
        textView = (TextView) findViewById(R.id.tv_text);

        btnChangeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //使用Handler发送消息实现子线程更新UI
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
