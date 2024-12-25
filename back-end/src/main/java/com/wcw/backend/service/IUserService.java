package com.wcw.backend.service;

import com.wcw.backend.entity.User;

import java.util.List;

public interface IUserService {
	List<User> findAll();
}
