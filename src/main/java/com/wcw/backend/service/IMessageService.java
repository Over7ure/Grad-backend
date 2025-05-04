package com.wcw.backend.service;

/**
 * 消息服务接口
 * 定义消息发送和Redis缓存操作功能
 */
public interface IMessageService {
	/**
	 * 发送消息到RabbitMQ队列
	 *
	 * @param message 要发送的消息内容
	 */
	void sendMessage(String message);

	/**
	 * 将数据保存到Redis
	 *
	 * @param key   Redis的键
	 * @param value 要保存的值
	 */
	void saveToRedis(String key, Object value);

	/**
	 * 从Redis获取数据
	 *
	 * @param key Redis的键
	 * @return 存储在Redis中的值
	 */
	Object getFromRedis(String key);

	/**
	 * 将数据保存到Redis并设置过期时间
	 *
	 * @param key               Redis的键
	 * @param value             要保存的值
	 * @param expirationSeconds 过期时间（秒）
	 */
	void saveToRedis(String key, Object value, long expirationSeconds);

	/**
	 * 删除Redis中的键值对
	 *
	 * @param key Redis的键
	 */
	void deleteFromRedis(String key);
}
