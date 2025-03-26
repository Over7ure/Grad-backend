package com.wcw.backend.controller;

import com.wcw.backend.result.Result;
import com.wcw.backend.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
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
		return switch (result) {
			case 0 -> new Result(200, "Login successful");
			case 1 -> new Result(401, "Password error");
			case 2 -> new Result(402, "User is disabled");
			default -> new Result(403, "User not found");
		};
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
