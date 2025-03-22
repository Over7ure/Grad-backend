package com.wcw.backend.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一响应结果类
 * 用于封装API的返回结果，包含状态码和消息
 */
@Getter
@Setter
@AllArgsConstructor
public class Result {
	/**
	 * 响应状态码
	 */
	private int code;
	
	/**
	 * 响应消息
	 */
	private String message;
}
