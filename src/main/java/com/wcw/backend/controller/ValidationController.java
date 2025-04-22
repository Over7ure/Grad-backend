package com.wcw.backend.controller;

import com.wcw.backend.result.Result;
import com.wcw.backend.service.IValidationService;
import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户验证控制器
 * 处理登录、注册等用户验证操作
 */
@RestController
@RequestMapping("/api")
public class ValidationController {
	@Autowired
	private IValidationService validationService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 用户登录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录结果
	 */
	@PostMapping("/login")
	public Result login(@RequestParam String username, @RequestParam String password) {
		int result = validationService.validateUser(username, password);
		if (result == 0) {
			UserInfo user = userMapper.findByUsername(username);
			if (user == null) {
				return new Result(403, "User not found");
			}
			Long userId = user.getUid() == null ? null : user.getUid().longValue();
			if (userId == null) {
				return new Result(403, "UserId not found");
			}
			String token = JwtUtil.createToken(userId);
			String redisKey = "login:token:" + userId;
			stringRedisTemplate.opsForValue().set(redisKey, token);
			// 返回token和登录成功信息
			return new Result(200, "Login successful", token);
		} else if (result == 1) {
			return new Result(401, "Password error");
		} else if (result == 2) {
			return new Result(402, "User is disabled");
		} else {
			return new Result(403, "User not found");
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param email 电子邮箱
	 * @param username 用户名
	 * @param password 密码
	 * @return 注册结果
	 */
	@PostMapping("/register")
	public Result register(@RequestParam String email, @RequestParam String username, @RequestParam String password) {
		return validationService.registerUser(email, username, password)
				? new Result(200, "Registration successful")
				: new Result(400, "Registration failed");
	}
}
