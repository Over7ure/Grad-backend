package com.wcw.backend.service.impl;

import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public int validateUser(String username, String password) {
		UserInfo user = userMapper.findByUsername(username);

		// 0: success; 1: password error; 2: user is disabled; 3: user not found
		if (user != null) {
			if (user.getIsDisable().equals(0)) {
				return user.getPassword().equals(password) ? 0 : 1;
			} else {
				return 2;
			}
		} else {
			return 3;
		}
	}
}
