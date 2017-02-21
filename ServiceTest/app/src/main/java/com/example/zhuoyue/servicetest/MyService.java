package com.example.zhuoyue.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    public DownloadBinder mBinder = new DownloadBinder();
    /*在Binder中写可以在Activity中可以调用的方法*/
    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d(TAG, "startDownload executed");
        }

        public int getProgress(){
            Log.d(TAG, "getProgress executed");
            return 0;
        }
    }
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        //使用前台Service
        Intent intent= new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);//注意不是notificationManager.notify()
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
