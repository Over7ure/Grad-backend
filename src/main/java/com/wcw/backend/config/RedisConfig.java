package com.wcw.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 用于配置RedisTemplate及其序列化方式
 */
@Configuration
public class RedisConfig {

	/**
	 * 配置RedisTemplate，定制序列化器
	 *
	 * @param redisConnectionFactory Redis连接工厂
	 * @return 配置好的RedisTemplate实例
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);

		// 使用StringRedisSerializer来序列化和反序列化redis的key
		template.setKeySerializer(new StringRedisSerializer());

		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		return template;
	}
}
