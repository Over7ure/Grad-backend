package com.wcw.backend.controller;

import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 * 处理用户查询相关操作
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private IUserService userService;

	/**
	 * 查询所有用户
	 * 
	 * @return 用户列表
	 */
	@GetMapping("/findAll")
	public List<UserInfo> findAll() {
		return userService.findAll();
	}

	/**
	 * 分页查询用户
	 * 
	 * @param pageNum 页码
	 * @param pageSize 每页数量
	 * @param username 用户名(用于模糊查询)
	 * @return 分页结果
	 */
	@GetMapping("/findPage")
	public Map<String, Object> findPage(@RequestParam Integer pageNum,
	                                    @RequestParam Integer pageSize,
	                                    @RequestParam String username) {
		return userService.findPage(pageNum, pageSize, username);
	}
}
