package com.ivsom.ops.app.utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author HanN on 2019/11/13 16:04
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 执行下载任务
 *                将下载状态返回到service
 *                同时获取service发来的状态
 * @updateuser:
 * @updatedata: 2019/11/13 16:04
 * @updateremark:
 * @version: 2.1.67
 */
public class DownloadTask extends AsyncTask<String ,Integer,Integer> {
   //下载状态
    public static final int TYPE_SUCCESS = 0;
   public static final int TYPE_FAILED = 1;
   public static final int TYPE_PAUSED = 2;
   public static final int TYPE_CANCELED = 3;
   //下载监听
   private DownloadListener listener;
    //下载状态
    private boolean isCanceled = false;

    private boolean isPaused = false;

    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            //remark loaded length
            long downLoadedLength = 0;
            // get down url
            String downLoadUrl = strings[0];
            //get down filename
            String filename = downLoadUrl.substring(downLoadUrl.lastIndexOf("/"));
            //get filestorage system down filepath
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            //create down file
            file = new File(directory+filename);
            if (file.exists()) {
                downLoadedLength = file.length();
            }
            //get down file all length
            long contentLength = getContentLength(downLoadUrl);
            if (contentLength == 0) {
                return TYPE_FAILED;
            }else if (contentLength == downLoadedLength) {
                return TYPE_SUCCESS;
            }
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANG","btye=" + downLoadedLength +"-")
                    .url(downLoadUrl)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downLoadedLength);
                byte[] bytes = new byte[1024];
                int total = 0;
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(bytes,0,len);
                        int process = (int) ((total + downLoadedLength) * 100 / contentLength);
                        publishProgress(process);
                    }
                }

                response.body().close();
                return TYPE_SUCCESS;
            }



        }catch (Exception e) {

        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    /**
     *doInBackground  执行回调
     * @param status
     */
    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
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
            default:
                break;
        }
    }

    /**
     * publishProgress 执行回调 获取到下载进度process 交给listener 将下载进度回调给service
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int process = values[0];
        if (process > lastProgress) {
            listener.onProgress(process);
            lastProgress = process;
        }
    }


    /**
     * 暴露给service 让service通过binder 暴露给activity调用
     */
    public void  pauseDownLoad() {
        isPaused = true;
    }
    public void cancelDownLoad() {
        isCanceled = true;
    }

    /**
     * 获取文件大小
     * @param downloadUrl
     * @return
     * @throws IOException
     */
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Log.d("getContentLength", downloadUrl);
        Response response = client.newCall(request).execute();
        Log.d("getContentLength", "查看文件长度");
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
