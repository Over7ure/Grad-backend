package com.wcw.backend.mapper;

import com.wcw.backend.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户数据访问接口
 * 处理用户信息的数据库操作
 */
@Mapper
public interface UserMapper {
	/**
	 * 查询所有用户信息
	 *
	 * @return 用户列表
	 */
	List<UserInfo> findAll();

	/**
	 * 分页查询用户信息
	 *
	 * @param pageNum  页码偏移量
	 * @param pageSize 每页大小
	 * @param username 用户名（模糊查询条件）
	 * @return 分页的用户列表
	 */
	List<UserInfo> selectPage(Integer pageNum, Integer pageSize, String username);

	/**
	 * 获取符合条件的用户总数
	 *
	 * @param username 用户名（模糊查询条件）
	 * @return 用户总数
	 */
	Integer selectTotal(String username);

	/**
	 * 根据用户名查找用户
	 *
	 * @param username 用户名（精确匹配）
	 * @return 用户对象
	 */
	UserInfo findByUsername(String username);

	/**
	 * 保存用户信息
	 *
	 * @param userInfo 用户信息对象
	 */
	void save(UserInfo userInfo);
}
