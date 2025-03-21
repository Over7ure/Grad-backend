package com.wcw.backend.service;

import com.wcw.backend.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface IUserService {
	List<UserInfo> findAll();

	Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username);
}
