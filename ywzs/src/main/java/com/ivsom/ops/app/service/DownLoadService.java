package com.ivsom.ops.app.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.ivsom.ops.R;
import com.ivsom.ops.app.utils.DownloadListener;
import com.ivsom.ops.app.utils.DownloadTask;
import com.ivsom.ops.mvp.ui.activity.MainActivity;

import java.io.File;


/**
 * @author HanN on 2019/11/13 15:43
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 下载使用
 * @updateuser:
 * @updatedata: 2019/11/13 15:43
 * @updateremark:
 * @version: 2.1.67
 */
public class DownLoadService extends Service {

    // 2声明
    private DownBinder mBinder = new DownBinder();
    //   private DownloadTask downloadTask;
    private DownloadTask downloadTask;
    // 4 声明下载监听
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            //在通知栏上显示下载进度
            getNotificationManager().notify(1,getNotification("下载中...",progress));
        }

        @Override
        public void onSuccess() {
            //将downloadtask 清空
            downloadTask = null;
            //开启前台服务，通知栏中该通知也会变为不会随着点击或者滑动而删除
            stopForeground(true);
            //在通知栏上显示下载完成
            //

            getNotificationManager().notify(1,getNotification("下载成功",-1));
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载失败",-1));
        }

        @Override
        public void onPaused() {
            downloadTask = null;
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
        }
    };
    private String downloadUrl;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 3 返回这个mbinder
        return mBinder;
    }

    /**
     *  1 在binder里面创建方法
     */
    private class  DownBinder extends Binder {
        /**
         * 开始下载
         */
        public void startDownLoad(String url) {
            //调用downloadtask
            if (downloadTask != null) {
                downloadTask = new DownloadTask(downloadListener);
                //是为了方便在取消下载的时候使用
                downloadUrl = url;
                downloadTask.execute(url);
                //启动通知栏服务 ,否则就会失败 为啥啊
                getNotificationManager();
                //id 都是1 代办同类型通知栏 ,进度为0 表示开始
                startForeground(1, getNotification("下载中。。。", 0));

            }
        }
        public void pauseDownLoad() {
            if (downloadTask != null) {
                downloadTask.pauseDownLoad();
            }
        }
        public void cancelDownLoad() {
            if (downloadTask != null) {
                downloadTask.cancelDownLoad();
            }else {
                if (downloadUrl != null) {
                    // 取消下载时需要文件删除，并通知关闭
                    String filename = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + filename);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownLoadService.this, "已经取消下载", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public static final String CHANNEL_ID = "com.example.servicepractice";
    public static final String CHANNEL_NAME = "Download service practice";
    /**
     * 获取通知管理器
     * @return
     */
    private NotificationManager getNotificationManager() {
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = manager.getNotificationChannel(CHANNEL_ID);
            if (notificationChannel == null) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
            }
        }
        return manager;
    }


    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        // builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        builder.setOnlyAlertOnce(true); // 关闭重复播放声音
        if(progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
