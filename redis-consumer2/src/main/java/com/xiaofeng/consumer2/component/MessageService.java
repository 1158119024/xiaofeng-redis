package com.xiaofeng.consumer2.component;

import com.xiaofeng.consumer2.config.BeanConfig;
import com.xiaofeng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: 晓枫
 * @Date: 2019/4/6 16:03
 * @Description:
 */
@Component
public class MessageService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private BeanConfig beanConfig;

    @PostConstruct
    public void run() throws InterruptedException {
        while (true){
            Thread.sleep(100);
            if(beanConfig.isSpare()){
                User user = (User) redisTemplate.opsForList().rightPop("rymsg");
                if(user != null){
                    beanConfig.exec(user);
                }else{
                    System.out.println("当前无user:"+user);
                    Thread.sleep(5000);
                }
            }else{
                System.out.println("无空闲线程");
                Thread.sleep(5000);
            }
        }
    }



}
