package com.wcw.backend.service.impl;

import com.wcw.backend.entity.User;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}
}
