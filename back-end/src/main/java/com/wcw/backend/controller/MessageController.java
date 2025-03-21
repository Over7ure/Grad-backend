package com.wcw.backend.controller;

import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.service.IMessageService;
import com.wcw.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MessageController {
	@Autowired
	private IMessageService messageService;

	@Autowired
	private IUserService userService;

	@PostMapping("/sendMessage")
	public void sendMessage(@RequestBody Map<String, String> request) {
		messageService.sendMessage(request.get("message"));
	}

	@PostMapping("/saveToRedis")
	public void saveToRedis(@RequestBody Map<String, Object> request) {
		messageService.saveToRedis((String) request.get("key"), request.get("value"));
	}

	@GetMapping("/getFromRedis")
	public Object getFromRedis(@RequestParam String key) {
		return messageService.getFromRedis(key);
	}

	@PostMapping("/cacheUserData")
	public void cacheUserData() {
		List<UserInfo> users = userService.findAll();
		messageService.saveToRedis("userList", users);
	}
}
