package com.wcw.backend.controller;

import com.wcw.backend.result.Result;
import com.wcw.backend.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private ILoginService loginService;

	@PostMapping("/login")
	public Result Login(@RequestParam String username, @RequestParam String password) {
		int result = loginService.validateUser(username, password);
		if (result == 0) {
			return new Result(200, "Login successful");
		} else if (result == 1) {
			return new Result(401, "Password error");
		} else if (result == 2) {
			return new Result(402, "User is disabled");
		} else {
			return new Result(403, "User not found");
		}
	}
}
