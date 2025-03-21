package com.wcw.backend.mapper;

import com.wcw.backend.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
	List<UserInfo> findAll();

	List<UserInfo> selectPage(Integer pageNum, Integer pageSize, String username);

	Integer selectTotal(String username);

	UserInfo findByUsername(String username);

	void save(UserInfo userInfo);
}
