package com.example.zhuoyue.servicebesttest;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhuoyue on 2017/2/19.
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    //监控下载状态
    private boolean isCanceled = false;
    private boolean isPaused = false;

    private DownliadListener listener;

    private int lastProgress;

    public DownloadTask(DownliadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            long downloadLength = 0;
            //获取下载链接，文件名，存储目录
            String downloadUrl = params[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directotry = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

            /*获取已下载文件长度和文件总长度*/
            file = new File(directotry + fileName);
            if (file.exists()) {
                downloadLength = file.length();
            }
            long contentLength = 0;
            try {
                contentLength = getContentLength(downloadUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*判断本地文件状态*/
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (downloadLength == contentLength) {
                return TYPE_SUCCESS;
            }

            /*下载任务*/
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载，指定继续下载的位置
                    .addHeader("RANGE", "bytes =" + downloadLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadLength);//跳过已经下载的部分
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedFile.write(b, 0, len);
                        //计算已经下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);//刷新进度
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (savedFile != null)
                    savedFile.close();
                if (isCanceled && file != null)
                    file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            //刷新显示进度
            listener.onProgerss(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        //针对doInBackground的返回值进行操作
        switch (integer) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    /**
     * 获取待下载文件总长度
     *
     * @param downloadUrl
     * @return
     * @throws IOException
     */
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;

    }

}
