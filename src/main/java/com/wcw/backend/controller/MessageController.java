package com.wcw.backend.controller;

import com.wcw.backend.service.IMessageService;
import com.wcw.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 消息管理控制器
 * 处理消息发送和Redis缓存操作
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {
	@Autowired
	private IMessageService messageService;

	@Autowired
	private IUserService userService;

	/**
	 * 发送消息
	 *
	 * @param request 包含消息内容的请求体
	 */
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestBody Map<String, String> request) {
		messageService.sendMessage(request.get("message"));
	}

	/**
	 * 保存数据到Redis
	 *
	 * @param request 包含key和value的请求体
	 */
	@PostMapping("/saveToRedis")
	public void saveToRedis(@RequestBody Map<String, Object> request) {
		messageService.saveToRedis((String) request.get("key"), request.get("value"));
	}

	/**
	 * 从Redis获取数据
	 *
	 * @param key Redis键
	 * @return 对应的值
	 */
	@GetMapping("/getFromRedis")
	public Object getFromRedis(@RequestParam String key) {
		return messageService.getFromRedis(key);
	}
}
