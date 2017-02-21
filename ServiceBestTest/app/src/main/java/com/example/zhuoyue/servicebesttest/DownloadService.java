package com.example.zhuoyue.servicebesttest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

/**
 * 下载服务
 * Created by zhuoyue on 2017/2/19.
 */

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;

    private DownliadListener listener = new DownliadListener() {
        @Override
        public void onProgerss(int progress) {
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/")+1);
            getNotificationManager().notify(1, getNotification(fileName, progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功后关闭前台服务并创建一个下载成功通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this, "Download Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //下载成功后关闭前台服务并创建一个下载成功通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    };


    private DownloadBinder mbinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(String url) {
            //开启下载，创建前台服务
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_LONG).show();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            //取消下载，并删除已下载的文件，关闭通知
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directotry = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directotry + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    /**
     * 自定义快捷方法获取NotificationManager
     *
     * @return
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 自定义快捷方法获取Notification
     *
     * @param title
     * @param progress
     * @return
     */
    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress > 0) {
            //只有当进度大于0时才显示下载进度
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
