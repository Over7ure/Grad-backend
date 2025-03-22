package com.wcw.backend.entity;

import lombok.Data;

/**
 * 模型信息实体类
 * 存储缺陷检测模型的相关信息
 */
@Data
public class ModelInfo {
	// 模型ID，唯一标识
	private Integer modelId;
	// 模型名称
	private String modelName;
	// 模型文件路径
	private String modelUrl;
	// 是否启用，1表示启用，0表示禁用
	private Integer isEnabled;
}
