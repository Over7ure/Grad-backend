package com.wcw.backend.service.impl;

import com.wcw.backend.entity.UserInfo;
import com.wcw.backend.mapper.UserMapper;
import com.wcw.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 * 提供用户查询和分页功能
 */
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 查询所有用户
	 *
	 * @return 用户列表
	 */
	@Override
	public List<UserInfo> findAll() {
		return userMapper.findAll();
	}

	/**
	 * 分页查询用户
	 *
	 * @param pageNum  页码
	 * @param pageSize 每页数量
	 * @param username 用户名（模糊查询）
	 * @return 包含数据和总数的Map
	 */
	@Override
	public Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username) {
		pageNum = (pageNum - 1) * pageSize;
		username = "%" + username + "%";
		List<UserInfo> list = userMapper.selectPage(pageNum, pageSize, username);
		Integer count = userMapper.selectTotal(username);
		Map<String, Object> res = new HashMap<>();
		res.put("data", list);
		res.put("total", count);
		return res;
	}
}
