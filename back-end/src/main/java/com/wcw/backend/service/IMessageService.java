package com.wcw.backend.service;

public interface IMessageService {
	void sendMessage(String message);

	void saveToRedis(String key, Object value);

	Object getFromRedis(String key);
}
