package com.wcw.backend.service.impl;

import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证服务实现类
 * 提供用户登录验证和注册功能
 */
@Service
public class ValidationServiceImpl implements IValidationService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 验证用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 验证结果代码：0-成功; 1-密码错误; 2-用户已禁用; 3-用户不存在
	 */
	@Override
	public int validateUser(String username, String password) {
		UserInfo user = userMapper.findByUsername(username);

		if (user == null) {
			return 3; // 用户不存在
		}
		
		if (user.getIsDisable().equals(1)) {
			return 2; // 用户已禁用
		}
		
		return user.getPassword().equals(password) ? 0 : 1; // 密码验证
	}

	/**
	 * 注册新用户
	 * @param email 邮箱
	 * @param username 用户名
	 * @param password 密码
	 * @return 注册是否成功
	 */
	@Override
	public boolean registerUser(String email, String username, String password) {
		UserInfo existingUser = userMapper.findByUsername(username);
		if (existingUser != null) {
			return false; // 用户已存在
		}
		
		UserInfo newUser = new UserInfo();
		newUser.setEmail(email);
		newUser.setUsername(username);
		newUser.setPassword(password);
		userMapper.save(newUser);
		return true;
	}
}
