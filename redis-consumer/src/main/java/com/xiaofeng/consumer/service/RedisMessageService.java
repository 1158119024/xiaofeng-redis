package com.xiaofeng.consumer.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * @Auther: 晓枫
 * @Date: 2019/4/5 18:38
 * @Description:
 */
@Service
public class RedisMessageService {

    @Async
    public void exec(String msg){
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("接收到的消息是：" + msg + ",线程：" + Thread.currentThread().getName());
    }
}
