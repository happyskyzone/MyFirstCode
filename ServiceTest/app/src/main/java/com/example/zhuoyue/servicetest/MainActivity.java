package com.example.zhuoyue.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    public Button btnStartService;
    public Button btnStopService;
    public Button btnBindService;
    public Button btnUnbindService;
    public Button btnStartIntentService;

    /*Activity和Service进行通信,使用Binder*/
    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBtn();
        Log.d(TAG, "onCreate: Thread is "+Thread.currentThread().getId());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_service:
                //开启服务
                Intent startService = new Intent(this,MyService.class);
                startService(startService);
                break;
            case R.id.btn_stop_service:
                //关闭服务
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.btn_bind_service:
                //绑定服务到活动
                Intent bindService = new Intent(this,MyService.class);
                bindService(bindService,connection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                //解绑服务
                Intent unBindSevice = new Intent(this,MyService.class);
                unbindService(connection);
                break;
            case R.id.btn_start_intent_service:
                //启动IntentService,可以在此服务中执行耗时操作
                Intent intentService = new Intent(this,MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }

    private void initBtn(){
        btnStartService = (Button) findViewById(R.id.btn_start_service);
        btnStopService = (Button) findViewById(R.id.btn_stop_service);
        btnBindService = (Button) findViewById(R.id.btn_bind_service);
        btnUnbindService = (Button) findViewById(R.id.btn_unbind_service);
        btnStartIntentService= (Button) findViewById(R.id.btn_start_intent_service);
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnBindService.setOnClickListener(this);
        btnUnbindService.setOnClickListener(this);
        btnStartIntentService.setOnClickListener(this);
    }
}
