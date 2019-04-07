package com.xiaofeng.consumer2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: 晓枫
 * @Date: 2019/4/5 18:12
 * @Description:
 */
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {


    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;

    private void showThreadPoolInfo(String prefix){
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        if(null==threadPoolExecutor){
            return;
        }

//        System.out.println("getThreadNamePrefix:"+
//                this.getThreadNamePrefix()+",prefix:"+
//                prefix+",当前工作:"+
//                threadPoolExecutor.getTaskCount()+",getCompletedTaskCount():"+
//                threadPoolExecutor.getCompletedTaskCount()+",getActiveCount():"+
//                threadPoolExecutor.getActiveCount()+"getQueue().size():"+
//                threadPoolExecutor.getQueue().size());
        System.out.println("当前已经提交了"+threadPoolExecutor.getTaskCount()+"个任务," +
                "完成了"+threadPoolExecutor.getCompletedTaskCount()+"个," +
                "当前有"+threadPoolExecutor.getActiveCount()+"个线程在处理任务,"+
                "还剩"+threadPoolExecutor.getQueue().size()+"个任务在队列中等待"
        );
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("1. do execute");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo("2. do execute");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("1. do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("2. do submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("1. do submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("2. do submitListenable");
        return super.submitListenable(task);
    }
}
