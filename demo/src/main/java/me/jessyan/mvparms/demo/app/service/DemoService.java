/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.mvparms.demo.app.service;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jess.arms.base.BaseService;
import com.jess.arms.utils.LogUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import butterknife.BindView;

/**
 * ================================================
 * 展示 {@link BaseService} 的用法
 * <p>
 * Created by JessYan on 09/07/2016 16:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class DemoService extends BaseService {
    private static final String TAG = "DemoService";

    private LocalBinder localBinder = new LocalBinder();
    /**
     * 先于onCreate执行
     */
    @Override
    public void init() {
        LogUtils.debugInfo(TAG,"init");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.debugInfo(TAG,"onBind");
        return  localBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.debugInfo(TAG,"onCreate");
    }


    /**
     * 创建 子类binder  让前端通过binder 实现对service 的交互
     */
    public class LocalBinder extends Binder {
        /**
         * 通过binder 让前端获取到service 从而可以调用service里面的方法
         * @return
         */
        public DemoService getService() {
            return DemoService.this;
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
     * 自动注册eventbus
     * @return true
     */
    @Override
    public boolean useEventBus() {
        LogUtils.debugInfo(TAG,"useEventBus");
        return super.useEventBus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.debugInfo(TAG,"onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.debugInfo(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtils.debugInfo(TAG,"onLowMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtils.debugInfo(TAG,"onConfigurationChanged");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtils.debugInfo(TAG,"onTrimMemory");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.debugInfo(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.debugInfo(TAG,"onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        LogUtils.debugInfo(TAG,"onTaskRemoved");
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
        LogUtils.debugInfo(TAG,"dump");
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
