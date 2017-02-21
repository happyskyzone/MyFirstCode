package com.example.zhuoyue.servicebesttest;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStartDownload;
    private Button btnPauseDownload;
    private Button btnCancelDownload;

    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化控件

        //开启服务，绑定服务
        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);

        //读写外置存储是危险权限，需要申请运行时权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }

    private void initView() {
        btnStartDownload = (Button) findViewById(R.id.btn_start_download);
        btnPauseDownload = (Button) findViewById(R.id.btn_pause_download);
        btnCancelDownload = (Button) findViewById(R.id.btn_cancel_download);
        btnStartDownload.setOnClickListener(this);
        btnPauseDownload.setOnClickListener(this);
        btnCancelDownload.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder==null){
            return;
        }
        switch (v.getId()){
            case R.id.btn_start_download:
                String url = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
                downloadBinder.startDownload(url);
                break;
            case R.id.btn_pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.btn_cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
