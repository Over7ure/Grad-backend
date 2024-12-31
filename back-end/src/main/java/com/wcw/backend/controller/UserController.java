package com.wcw.backend.controller;

import com.wcw.backend.entity.User;
import com.wcw.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@GetMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping("/findPage")
	public Map<String, Object> findPage(@RequestParam Integer pageNum,
	                                    @RequestParam Integer pageSize,
	                                    @RequestParam String username) {
		return userService.findPage(pageNum, pageSize, username);
	}
}
