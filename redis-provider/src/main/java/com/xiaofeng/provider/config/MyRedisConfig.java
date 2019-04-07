package com.xiaofeng.provider.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * 配置RedisTemplate使用json序列化，这样保存到redis里的数据就是json数据了，方便观看
 */
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        template.setKeySerializer(redisSerializer);
        return template;
    }

//    @Primary  //将某个缓存管理器作为默认的
//    @Bean
//    public RedisCacheManager myCacheManager(RedisTemplate<Object, Object> myRedisTemplate){
//        RedisCacheManager cacheManager = new RedisCacheManager(myRedisTemplate);
//        //key多了一个前缀
//
//        //使用前缀，默认会将CacheName作为key的前缀
//        cacheManager.setUsePrefix(true);
//
//        return cacheManager;
//    }

}
