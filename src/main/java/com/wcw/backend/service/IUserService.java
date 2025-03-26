package com.wcw.backend.service;

import com.wcw.backend.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户服务接口
 * 定义用户查询和分页功能
 */
public interface IUserService {
	/**
	 * 查询所有用户
	 * @return 用户列表
	 */
	List<UserInfo> findAll();

	/**
	 * 分页查询用户
	 * @param pageNum 页码
	 * @param pageSize 每页数量
	 * @param username 用户名（模糊查询）
	 * @return 包含数据和总数的Map
	 */
	Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username);
}
