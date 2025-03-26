package com.wcw.backend.entity;

import lombok.Data;

/**
 * 缺陷类型实体类
 * 用于存储和管理系统中不同类型的缺陷信息
 */
@Data
public class DefectType {
	// 缺陷ID，唯一标识
	private Integer defectId;
	// 缺陷名称
	private String defectName;
	// 缺陷描述，详细说明缺陷的特征
	private String defectDesc;
	// 是否启用，1表示启用，0表示禁用
	private Integer isEnabled;
}
