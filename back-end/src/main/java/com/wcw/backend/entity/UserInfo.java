package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
	private Integer uid;
	private String username;
	private String password;
	private Integer fileNum;
	private Integer role;
	private String email;
	private Integer isDisable;
	private Date createDate;
}