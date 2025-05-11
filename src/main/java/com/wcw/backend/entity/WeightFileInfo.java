package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

/**
 * 权重文件信息实体类
 * 存储缺陷检测权重文件的相关信息
 */
@Data
public class WeightFileInfo {
	// 权重文件ID，唯一标识
	private Integer weightFileId;
	// 是否被选中，1表示启用，0表示禁用
	private Integer isSelected;
	// 权重文件训练epochs
	private Integer epochs;
	// 创建时间
	private Date createTime;
}
