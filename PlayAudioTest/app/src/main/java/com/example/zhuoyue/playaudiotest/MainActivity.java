package com.example.zhuoyue.playaudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay = (Button) findViewById(R.id.btn_play);
        Button btnPause = (Button) findViewById(R.id.btn_pause);
        Button btnStop= (Button) findViewById(R.id.btn_stop);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            initMediaPlayer();
        }
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            player.setDataSource(file.getPath());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else {
                    Toast.makeText(MainActivity.this,"获取读写SD卡权限失败",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                if (!player.isPlaying())
                    player.start();
                break;
            case R.id.btn_pause:
                if (player.isPlaying())
                    player.pause();
                break;
            case R.id.btn_stop:
                if (player.isPlaying()){
                    player.reset();
                    initMediaPlayer();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player!=null){
            player.stop();
            player.release();
        }
    }
}
