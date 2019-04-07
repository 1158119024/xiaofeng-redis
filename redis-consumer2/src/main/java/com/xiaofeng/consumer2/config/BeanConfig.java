package com.xiaofeng.consumer2.config;

import com.xiaofeng.entity.User;
import io.rong.RongCloud;
import io.rong.messages.TxtMessage;
import io.rong.methods.message._private.Private;
import io.rong.models.message.PrivateMessage;
import io.rong.models.response.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: 晓枫
 * @Date: 2019/4/5 17:54
 * @Description:
 */
@Configuration
@EnableAsync
public class BeanConfig {

    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.name.prefix}")
    private String namePrefix;

    private ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();

    private static String appKey = "8luwapkv8jotl";
    private static String appSecret = "CLreS4FCxQ8s";

    @Bean
    public TaskExecutor taskExecutor() {
        System.out.println(corePoolSize);
        // 设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 设置队列容量
        executor.setQueueCapacity(queueCapacity);
        // 设置线程活跃时间（秒）
//        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix(namePrefix);
        // 设置拒绝策略
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
//        executor.setWaitForTasksToCompleteOnShutdown(true);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    public boolean isSpare(){
        System.out.println(corePoolSize+","+executor.getActiveCount());
        return executor.getActiveCount() >= corePoolSize ? false : true;
    }

    @Async
    public void exec(User user){
        try {
            sendMessage(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("接收到的消息是：" + user + ",线程：" + Thread.currentThread().getName());
    }

    public void sendMessage(User user) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        //自定义 api 地址方式
        //RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret,api);
        Private msgPrivate = rongCloud.message.msgPrivate;
        String[] targetIds = {"2"};
        TxtMessage txtMessage = new TxtMessage(user.getContent(), user.getType());
        PrivateMessage privateMessage = new PrivateMessage()
                .setSenderId("1")
                .setTargetId(targetIds)
                .setObjectName(user.getType())
                .setContent(txtMessage)
                .setPushContent("")
                .setPushData("{\"pushData\":\"hello\"}")
                .setCount("4")
                .setVerifyBlacklist(0)
                .setIsPersisted(0)
                .setIsCounted(0)
                .setIsIncludeSender(0);
        ResponseResult privateResult = msgPrivate.send(privateMessage);
        System.out.println(privateResult);
    }
}
