package com.wcw.backend.service.impl;

import com.wcw.backend.service.IMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageService {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void sendMessage(String message) {
		rabbitTemplate.convertAndSend("myQueue", message);
	}

	@Override
	public void saveToRedis(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public Object getFromRedis(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
