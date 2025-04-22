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
public class Result {
	/**
	 * 响应状态码
	 */
	private int code;
	
	/**
	 * 响应消息
	 */
	private String message;
	
	/**
	 * 可选data字段
	 */
	private Object data;

	public Result(int code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
	}

	public Result(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
