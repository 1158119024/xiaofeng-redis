package com.xiaofeng.consumer.component;

import com.xiaofeng.consumer.service.RedisMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;


/**
 * @Auther: 晓枫
 * @Date: 2019/4/5 17:42
 * @Description:
 */
@Component
public class RedisMessage implements MessageListener {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisMessageService redisMessageService;

    static int count = 0;
    // 订阅
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("取出一个消息");
        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        String msg = serializer.deserialize(message.getBody());
        System.out.println("接收到的消息是：" + msg);
        redisMessageService.exec(msg);
        count++;
        System.out.println(count);
    }



}
