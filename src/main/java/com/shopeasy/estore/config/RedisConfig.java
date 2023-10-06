package com.shopeasy.estore.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;


@Configuration
@EnableCaching
public class RedisConfig {

//    @Bean
//    public JedisConnectionFactory connectionFactory(){
//        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName("localhost");
//        redisStandaloneConfiguration.setPort(6379);
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Bean
//    public RedisTemplate<String,Object> redisTemplate(){
//        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory());
//        redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
