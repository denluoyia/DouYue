package com.denluoyia.douyue.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 简单的线程池管理类
 */
@SuppressWarnings("unused")
public class ThreadManager {

    private static final String DEFAULT_SINGLE_THREAD_POOL_NAME = "DEFAULT_SINGLE_THREAD_POOL_NAME";

    //线程池代理 用于短时操作
    private static ThreadPoolProxy mShortPool = null;
    //短时操作线程池同步锁
    private static final Object mShortPoolLock = new Object();

    //线程池代理 用于长耗时操作
    private static ThreadPoolProxy mLongPool = null;
    private static final Object mLongPoolLock = new Object();

    private static final Object mSingleLock = new Object();
    private static Map<String, ThreadPoolProxy> mMap = new HashMap<>();

    /**
     * 获取一个用于执行短耗时任务的线程池，避免因为和耗时长的任务处在同一个队列而长时间得不到执行，通常用来执行本地的IO/SQL
     */
    public static ThreadPoolProxy getShortPool() {
        if (null == mShortPool) {
            synchronized (mShortPoolLock) {
                if (null == mShortPool) {
                    mShortPool = new ThreadPoolProxy(2, 2, 2L);
                }
            }
        }
        return mShortPool;
    }

    /**
     * 获取一个用于执行长耗时任务的线程池
     */
    public static ThreadPoolProxy getLongPool(){
        if (null == mLongPool){
            synchronized (mLongPoolLock){
                if (null == mLongPool){
                    mLongPool = new ThreadPoolProxy(5, 5, 5L);
                }
            }
        }
        return mLongPool;
    }

    public static ThreadPoolProxy getSinglePool(){
        return getSinglePool(DEFAULT_SINGLE_THREAD_POOL_NAME);
    }

    /** 单线程池 */
    private static ThreadPoolProxy getSinglePool(String name){
        synchronized (mSingleLock){
            ThreadPoolProxy singlePool = mMap.get(name);
            if (null == singlePool){
                singlePool = new ThreadPoolProxy(1, 1, 5L);
                mMap.put(name, mShortPool);
            }

            return singlePool;
        }
    }


    /** 线程池代理 */
    public static class ThreadPoolProxy {
        private ThreadPoolExecutor mExecutor;
        private final int mCorePoolSize; //核心池的大小
        private final int mMaximumPoolSize; //最大线程数
        private final long mKeepAliveTime;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime){
            this.mCorePoolSize = corePoolSize;
            this.mMaximumPoolSize = maximumPoolSize;
            this.mKeepAliveTime = keepAliveTime;
        }

        /**
         * 双重检查加锁 只有在第一次实例化的时候才启用同步机制，提高性能
         */
        private void initThreadPoolExecutor(){
            if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
                synchronized (ThreadPoolProxy.class){
                    /**
                     * corePoolSize: 核心线程，当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；只有当工作队列满了的情况下才会创建超出这个数量的线程。如果某个线程的空闲时间超过了活动时间，那么将标记为可回收，并且只有当线程池的当前大小超过corePoolSize时该线程才会被终止
                     * maximumPoolSize：线程池最大线程数,线程池中最多能创建的线程数，大于就会丢弃处理
                     * keepAliveTime: 表示线程没有任务执行时最多保持多久时间会终止
                     * unit: 时间单位
                     * workQueue: 一个阻塞队列，用来存储等待执行的任务
                     * threadFactory: 线程工厂，主要用来创建线程
                     * handler: 有四种策略：
                     */

                    if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
                        BlockingDeque workQueue = new LinkedBlockingDeque();
                        ThreadFactory threadFactory = Executors.defaultThreadFactory();
                        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                        mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, mKeepAliveTime, TimeUnit.MILLISECONDS, workQueue, threadFactory, handler);
                    }
                }
            }
        }

        /** 执行任务 */
        public void execute(Runnable task){
            initThreadPoolExecutor();
            mExecutor.execute(task);
        }

        /** 提交任务 */
        public Future submit(Runnable task){
            initThreadPoolExecutor();
            return mExecutor.submit(task);
        }

        /** 移除任务 */
        public void remove(Runnable task){
            initThreadPoolExecutor();
            mExecutor.remove(task);
        }
    }
}
