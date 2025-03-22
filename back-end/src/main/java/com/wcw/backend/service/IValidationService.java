package com.wcw.backend.service;

public interface IValidationService {
	//Login validation
	int validateUser(String username, String password);

	//Register validation
	boolean registerUser(String email, String username, String password);
}
