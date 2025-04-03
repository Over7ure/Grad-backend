package com.wcw.backend.entity;

import lombok.Data;

/**
 * 生产历史数据实体类
 * 记录产品生产的历史数据和缺陷统计信息
 */
@Data
public class ProdHistoryData {
	// 生产日期
	private String prodDate;
	// 生产数量
	private Integer prodNum;
	// 缺陷数量
	private Integer defectNum;
	// 针孔缺陷数量
	private Integer pinNum;
	// 气泡缺陷数量
	private Integer bubbleNum;
	// 划痕缺陷数量
	private Integer scratchNum;
	// 锡灰缺陷数量
	private Integer tinAshNum;
}
