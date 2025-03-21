package com.wcw.backend.controller;

import com.wcw.backend.result.Result;
import com.wcw.backend.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {
	@Autowired
	private IRegisterService registerService;

	@PostMapping("/register")
	public Result register(@RequestParam String email, @RequestParam String username, @RequestParam String password) {
		return registerService.registerUser(email, username, password) ? new Result(200, "Registration successful") : new Result(400, "Registration failed");
	}
}