package com.wcw.backend.entity;

import lombok.Data;

@Data
public class ModelInfo {
	private Integer modelId;
	private String modelName;
	private String modelUrl;
	private Integer isEnabled;
}