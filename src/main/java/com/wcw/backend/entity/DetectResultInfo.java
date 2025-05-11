package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

/**
 * 检测结果信息实体类
 * 记录每次缺陷检测的详细信息和结果
 */
@Data
public class DetectResultInfo {
	// 测试序列号，唯一标识一次检测
	private Integer testSn;
	// 图片ID，关联到原始图片
	private Integer picId;
	// 模型ID，关联到检测使用的模型
	private Integer weightFileId;
	// 显示状态，表示检测结果的展示状态
	private String showStatus;
	// 检测时间，记录检测的具体时间点
	private Date detectTime;
}
