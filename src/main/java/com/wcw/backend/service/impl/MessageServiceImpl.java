package com.wcw.backend.service.impl;

import com.wcw.backend.service.IMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 消息服务实现类
 * 提供消息发送和Redis缓存操作功能
 */
@Service
public class MessageServiceImpl implements IMessageService {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 发送消息到RabbitMQ队列
	 *
	 * @param message 要发送的消息内容
	 */
	@Override
	public void sendMessage(String message) {
		rabbitTemplate.convertAndSend("myQueue", message);
	}

	/**
	 * 将数据保存到Redis
	 *
	 * @param key   Redis的键
	 * @param value 要保存的值
	 */
	@Override
	public void saveToRedis(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 从Redis获取数据
	 *
	 * @param key Redis的键
	 * @return 存储在Redis中的值
	 */
	@Override
	public Object getFromRedis(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 将数据保存到Redis并设置过期时间
	 *
	 * @param key               Redis的键
	 * @param value             要保存的值
	 * @param expirationSeconds 过期时间（秒）
	 */
	@Override
	public void saveToRedis(String key, Object value, long expirationSeconds) {
		redisTemplate.opsForValue().set(key, value, expirationSeconds, TimeUnit.SECONDS);
	}

	/**
	 * 删除Redis中的键值对
	 *
	 * @param key Redis的键
	 */
	@Override
	public void deleteFromRedis(String key) {
		redisTemplate.delete(key);
	}
}
