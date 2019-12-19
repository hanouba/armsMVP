package com.hansen.fullvideo.thread;



import com.hansen.fullvideo.utils.LogUtils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**

 * remark:线程池管理的工具类
 */
public class ThreadPollProxy {

    private final String TAG = ThreadPollProxy.class.getSimpleName();

    private ThreadPoolExecutor poolExecutor;//线程池执行者 ，java内部通过该api实现对线程池管理

    public ThreadPollProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, String poolName) {
        if (poolExecutor == null || poolExecutor.isShutdown()) {
            poolExecutor = new ThreadPoolExecutor(
                    //核心线程数量
                    corePoolSize,
                    //最大线程数量
                    maximumPoolSize,
                    //当线程空闲时，保持活跃的时间
                    keepAliveTime,
                    //时间单元
                    unit,
                    //线程任务队列
                    new LinkedBlockingQueue<Runnable>(),
                    //创建线程的工厂
                    new DefaultThreadFactory(Thread.NORM_PRIORITY, poolName + " - ")
            );
        }
    }

    //对外提供一个执行任务的方法
    public void execute(Runnable r) {
        if (poolExecutor != null || !poolExecutor.isShutdown()) {
            poolExecutor.execute(r);
        }
    }

    /**
     * 从线程池中移除任务
     */
    public void remove(Runnable r) {
        if (poolExecutor != null) {
            if (r != null) {
                LogUtils.i(TAG, "remove Runnable");
                try {
                    if (null != r) {
                        try {
                            Thread.sleep(150);
                            Thread.interrupted();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        poolExecutor.remove(r);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    r = null;
                }
            }
        }
    }

    /**
     * 停止所有的子线程任务
     */
    public void stopAllThread() {
        if (poolExecutor != null) {
            try {
                LogUtils.i(TAG, "stopAllThread shutdown");
                //shutdown调用后，不可以再submit新的task，已经submit的将继续执行。
                poolExecutor.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.i(TAG, "stopAllThread shutdown Fail");
            }

            try {
                LogUtils.i(TAG, "stopAllThread shutdownNow");
                //shutdownNow试图停止当前正执行的task，并返回尚未执行的task的list
                poolExecutor.shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.i(TAG, "stopAllThread shutdownNow Fail");
            }
            poolExecutor = null;
        }
    }


    /**
     * 创建线程的工厂，设置线程的优先级，group，以及命名
     */
    private class DefaultThreadFactory implements ThreadFactory {
        /**
         * 线程池的计数
         */
        private final AtomicInteger poolNumber = new AtomicInteger(1);

        /**
         * 线程的计数
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            t.setPriority(threadPriority);
            return t;
        }
    }
}
