package com.wcw.backend.service.impl;

import com.wcw.backend.entity.User;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean validateUser(String username, String password) {
		User user = userMapper.findByUsername(username);
		return user != null && user.getPassword().equals(password);
	}
}
