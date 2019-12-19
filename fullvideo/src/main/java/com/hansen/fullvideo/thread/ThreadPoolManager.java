package com.hansen.fullvideo.thread;


import com.hansen.fullvideo.LogUtils;

import java.util.concurrent.TimeUnit;

/**
 *
 * 说明:对线程池进行管理和封装
 */
public class ThreadPoolManager {

    private static final String TAG = ThreadPoolManager.class.getSimpleName();

    //存活时间,表示最大线程池中等待任务的存活时间
    private long keepAliveTime;

    //存活时间的时间单位
    private TimeUnit unit;
    /**
     * 获取CPU数量
     */
    private int processors;

    //核心线程池数量，表示能够同时执行的任务数量
    private int corePoolSize;

    //最大线程池数量，其实是包含了核心线程池数量在内的
    private int maximumPoolSize;


    //sip线程池对象
    private ThreadPollProxy sipThreadPool;

    //RTP线程池对象
    private ThreadPollProxy videoThreadPool;

    //RTP线程池对象
    private ThreadPollProxy audioThreadPool;

    //log线程池对象
    private ThreadPollProxy logThreadPool;

    //mq线程池对象
    private ThreadPollProxy mqThreadPool;

    //临时事件线程池对象
    private ThreadPollProxy tempThreadPool;

    //线程管理单例对象
    private static ThreadPoolManager mInstance;

    /**
     * 获取线程管理对象
     *
     * @return
     */
    public static ThreadPoolManager getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }

    public ThreadPoolManager() {
        keepAliveTime = 1;//存活时间,表示最大线程池中等待任务的存活时间
        unit = TimeUnit.SECONDS;//存活时间的时间单位
        processors = Runtime.getRuntime().availableProcessors();

        //核心线程池数量的计算规则：当前设备的可用处理器核心数*2 + 1,能够让CPU得到最大效率的发挥；
        corePoolSize = processors * 2 + 1;

        //最大线程池数量，其实是包含了核心线程池数量在内的
        maximumPoolSize = processors * 4 + 1;//不能是0，否则报错
    }

    //------------------------流处理事件线程池------------------------

    /**
     * 执行与音视频流有关的子线程任务
     */
    public void executeVideoThread(Runnable r) {
        if (videoThreadPool == null) {
            videoThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "video");
        }
        if (r != null) {
            videoThreadPool.execute(r);
        }
    }

    /**
     * 关闭与音视频流有关的子线程任务
     */
    public void removeVideoThread(Runnable r) {
        if (videoThreadPool != null && r != null) {
            videoThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有与音视频流有关的子线程任务
     */
    public void closeVideoThreadPool() {
        if (videoThreadPool != null) {
            LogUtils.i(TAG, "closeVideoThreadPool");
            videoThreadPool.stopAllThread();
            videoThreadPool = null;
        }
    }


    //------------------------日志记录处理事件线程池------------------------

    /**
     * 执行与音视频流有关的子线程任务
     */
    public void executeLogThread(Runnable r) {
        if (logThreadPool == null) {
            logThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "Log");
        }
        if (r != null) {
            logThreadPool.execute(r);
        }
    }

    /**
     * 关闭与音视频流有关的子线程任务
     */
    public void removeLogThread(Runnable r) {
        if (logThreadPool != null && r != null) {
            logThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有与音视频流有关的子线程任务
     */
    public void closeLogThreadPool() {
        if (logThreadPool != null) {
            LogUtils.i(TAG, "closeLogThreadPool");
            logThreadPool.stopAllThread();
            logThreadPool = null;
        }
    }

    //------------------------临时事件线程池------------------------

    /**
     * 执行临时的子线程任务
     */
    public void executeCmdThread(Runnable r) {
        if (mqThreadPool == null) {
            mqThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "cmd");
        }
        if (r != null) {
            mqThreadPool.execute(r);
        }
    }

    /**
     * 关闭临时的子线程任务
     */
    public void removeCmdThread(Runnable r) {
        if (mqThreadPool != null && r != null) {
            mqThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有临时的子线程任务
     */
    public void closeCmdThreadPool() {
        if (mqThreadPool != null) {
            LogUtils.i(TAG, "closeCmdThreadPool");
            mqThreadPool.stopAllThread();
            mqThreadPool = null;
        }
    }

    //------------------------临时事件线程池------------------------

    /**
     * 执行临时的子线程任务
     */
    public void executeTempThread(Runnable r) {
        if (tempThreadPool == null) {
            tempThreadPool = new ThreadPollProxy(corePoolSize, maximumPoolSize, keepAliveTime, unit, "Temp");
        }
        if (r != null) {
            tempThreadPool.execute(r);
        }
    }

    /**
     * 关闭临时的子线程任务
     */
    public void removeTempThread(Runnable r) {
        if (tempThreadPool != null && r != null) {
            tempThreadPool.remove(r);
        }
    }

    /**
     * 关闭所有临时的子线程任务
     */
    public void closeTempThreadPool() {
        if (tempThreadPool != null) {
            LogUtils.i(TAG, "closeTempThreadPool");
            tempThreadPool.stopAllThread();
            tempThreadPool = null;
        }
    }
}
