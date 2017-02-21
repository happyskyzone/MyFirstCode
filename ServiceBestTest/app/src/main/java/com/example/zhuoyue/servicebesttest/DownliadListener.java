package com.example.zhuoyue.servicebesttest;

/**
 * Created by zhuoyue on 2017/2/19.
 */

public interface DownliadListener {
    //通知进度
    void onProgerss(int progress);
    //下载成功后操作
    void onSuccess();
    //暂停后操作
    void onPaused();
    //下载失败后操作
    void onFailed();
    //取消下载后操作
    void onCanceled();

}
