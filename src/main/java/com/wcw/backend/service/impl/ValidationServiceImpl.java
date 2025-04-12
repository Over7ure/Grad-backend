package com.wcw.backend.service.impl;

import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 验证服务实现类
 * 提供用户登录验证和注册功能
 */
@Service
public class ValidationServiceImpl implements IValidationService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 定义应用命名空间UUID，用于生成Version 5 UUID
	private static final UUID NAMESPACE = UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8"); // 使用标准命名空间UUID

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
		
		return passwordEncoder.matches(password, user.getPassword()) ? 0 : 1; // 密码验证
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
		if(email == null || username == null || password == null) {
			return false; // 输入参数不能为空
		}

		if(email.isEmpty() || username.isEmpty() || password.isEmpty()) {
			return false; // 输入参数不能为空
		}

		UserInfo existingUser = userMapper.findByUsername(username);
		if (existingUser != null) {
			return false; // 用户已存在
		}
		
		UserInfo newUser = new UserInfo();
		newUser.setEmail(email);
		newUser.setUsername(username);
		newUser.setPassword(passwordEncoder.encode(password));
		
		// 生成基于用户名的UUID Version 5
		int userId = generateUUIDFromUsername(username);
		newUser.setUid(userId);
		
		userMapper.save(newUser);
		return true;
	}
	
	/**
	 * 基于用户名生成UUID Version 5
	 * @param username 用户名
	 * @return 基于用户名的UUID
	 */
	private int generateUUIDFromUsername(String username) {
		UUID uuid = UUID.nameUUIDFromBytes(username.getBytes(StandardCharsets.UTF_8));
		String uuidString = uuid.toString();
		uuidString = uuidString.replace("-", "");
		int id =uuidString.hashCode();
		return id<0 ? -id : id;
	}
}
