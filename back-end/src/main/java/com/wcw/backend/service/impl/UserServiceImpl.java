package com.wcw.backend.service.impl;

import com.wcw.backend.entity.User;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

	@Override
	public Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username) {
		pageNum = (pageNum - 1) * pageSize;
		username = "%" + username + "%";
		List<User> list = userMapper.selectPage(pageNum, pageSize, username);
		Integer count = userMapper.selectTotal(username);
		Map<String, Object> res = new HashMap<>();
		res.put("data", list);
		res.put("total", count);
		return res;
	}
}
