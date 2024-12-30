package com.wcw.backend.service;

import com.wcw.backend.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
	List<User> findAll();

	Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username);
}
