package me.jessyan.mvparms.demo.app.service;

import android.app.Service;
import android.content.Intent;

import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jess.arms.utils.LogUtils;

/**
 * @author HanN on 2019/11/5 11:38
 * @email: 1356548475@qq.com
 * @project mvparms
 * @description: bind服务学习使用
 * @updateuser:
 * @updatedata: 2019/11/5 11:38
 * @updateremark:
 * @version: 2.1.67
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    private MyLocalBinder myLocalBinder = new MyLocalBinder();

    /**
     * bindservice 需要返回自定义的binder
     * 如果是启动服务可以为null
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myLocalBinder;
    }

    public class MyLocalBinder extends Binder {
        /**
         * 方便客户端调用服务的公用方法
         * @return
         */
        public MyService getService() {
            return MyService.this;
        }
        /**
         * binder 里面的方法
         */
        public void start() {
            LogUtils.debugInfo(TAG,"LocalBinder_start");
        }
        public void end() {
            LogUtils.debugInfo(TAG,"LocalBinder_end");
        }
    }


    /**
     * 服务里面对外的方法
     * @return
     */
    public  String myway(){
        LogUtils.debugInfo(TAG, "myway:hello world");
        return "hello world";
    }
}
