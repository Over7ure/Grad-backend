package com.wcw.backend.service.impl;

import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements IRegisterService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean registerUser(String email, String username, String password) {
		UserInfo existingUser = userMapper.findByUsername(username);
		if (existingUser != null) {
			return false; // User already exists
		}
		UserInfo newUser = new UserInfo();
		newUser.setEmail(email);
		newUser.setUsername(username);
		newUser.setPassword(password);
		userMapper.save(newUser);
		return true;
	}
}