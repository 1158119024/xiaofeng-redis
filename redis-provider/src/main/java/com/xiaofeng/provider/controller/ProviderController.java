package com.xiaofeng.provider.controller;

import com.xiaofeng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 晓枫
 * @Date: 2019/4/5 17:35
 * @Description:
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/index")
    public User redisIndex() {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("user", "user2");
        return null;
    }

    /**
     * 发布消息
     * @param id
     * @return
     */
    @RequestMapping("/sendMessage/{id}")
    public String sendMessage(@PathVariable String id) {
        User user = new User(1, 2, "你好"+id, "RC:TxtMsg");
        // 方式一：发布订阅  订阅者会直接取出消息，有丢消息风险。若订阅者处理过慢，发布的消息过多，还可能内存溢出。(redis-consumer)
        redisTemplate.convertAndSend("msg", user);
        // 方式二：利用redis list，先进先出，可以判断是否有空闲线程，有则取。(redis-consumer2)
        Long queue = redisTemplate.opsForList().leftPush("queue", user);

//        Object queue = redisTemplate.opsForList().rightPop("queue").toString();
        System.out.println(queue);
        return "";
    }

    /**
     * 发布消息
     * @return
     */
    @RequestMapping("/sendRYMessage")
    public String sendRYMessage(User user) {
        Long queue = redisTemplate.opsForList().leftPush("rymsg", user);
        System.out.println(queue);
        return "发送成功";
    }
}
