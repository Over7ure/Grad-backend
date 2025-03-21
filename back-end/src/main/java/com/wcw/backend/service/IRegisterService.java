package com.wcw.backend.service;

public interface IRegisterService {
	boolean registerUser(String email, String username, String password);
}