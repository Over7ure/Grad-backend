package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息实体类
 * 存储系统用户的基本信息和权限数据
 */
@Data
public class UserInfo {
	// 用户ID，唯一标识
	private Integer uid;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 用户角色，不同数值代表不同权限（0-管理员，1-普通用户）
	private Integer role;
	// 电子邮箱
	private String email;
	// 是否禁用，1表示禁用，0表示正常
	private Integer isDisable;
	// 创建日期
	private Date createDate;
}
