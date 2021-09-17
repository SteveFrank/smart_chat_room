package com.smart.cocurrent;

import com.google.common.util.concurrent.*;
import com.smart.util.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;


public class CallbackTaskScheduler  {
    /**
     * 方法二是使用自建的线程池时，专用于处理耗时操作
     */
    static ListeningExecutorService gPool = null;

    static {
        ExecutorService jPool = ThreadUtil.getMixedTargetThreadPool();
        gPool = MoreExecutors.listeningDecorator(jPool);
    }


    private CallbackTaskScheduler() {
    }

    /**
     * 添加任务
     * @param executeTask
     */
    public static <R> void add(CallbackTask<R> executeTask) {


        ListenableFuture<R> future = gPool.submit(new Callable<R>() {
            @Override
            public R call() throws Exception {
                R r = executeTask.execute();
                return r;
            }

        });

        Futures.addCallback(future, new FutureCallback<R>() {
            @Override
            public void onSuccess(R r) {
                executeTask.onBack(r);
            }
            @Override
            public void onFailure(Throwable t) {
                executeTask.onException(t);
            }
        });


    }

}
