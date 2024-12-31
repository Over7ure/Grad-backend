package com.wcw.backend.controller;

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
	public boolean Login(@RequestParam String username, @RequestParam String password) {
		return loginService.validateUser(username, password);
	}
}
