package com.wcw.backend.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ消息队列配置类
 * 负责定义和配置消息队列相关的Bean
 */
@Configuration
public class RabbitMQConfig {

	/**
	 * 创建一个名为"myQueue"的消息队列
	 * 
	 * @return 持久化的消息队列实例，服务器重启后队列仍然存在
	 */
	@Bean
	public Queue myQueue() {
		// 第二个参数true表示队列持久化，消息会被保存到磁盘
		return new Queue("myQueue", true);
	}
}
