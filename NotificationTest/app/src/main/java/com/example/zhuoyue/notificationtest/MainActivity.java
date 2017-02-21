package com.example.zhuoyue.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendNotification = (Button) findViewById(R.id.btn_send_notification);
        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置一个Intent为点击通知跳转到NotificationActivity
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                //发送通知
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())//显示通知时间
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setAutoCancel(true)//点击自动清除通知
                        .setContentIntent(pendingIntent)//点击通知事件
                        .setDefaults(NotificationCompat.DEFAULT_ALL)//设置通知声音，震动情况，震动需权限
                        //显示长文本
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText("长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本长文本"))
                        //显示图片
                        //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)))
                        //设置优先级,优先级高时将会变成弹窗横幅显示
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1, notification);
            }
        });
    }
}
