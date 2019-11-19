package com.ivsom.ops.app.utils;

/**
 * @author HanN on 2019/11/13 16:08
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 下载状态监听
 * @updateuser:
 * @updatedata: 2019/11/13 16:08
 * @updateremark:
 * @version: 2.1.67
 */
public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();

}
