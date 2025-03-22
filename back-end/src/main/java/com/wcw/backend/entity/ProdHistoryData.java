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
	// 当日生产数量
	private Integer prodNum;
	// 当日缺陷数量
	private Integer curDefectNum;
	// 当日良率
	private Float curYieldRate;
	// 累计生产总数
	private Integer totalNum;
	// 累计缺陷总数
	private Integer defectNum;
	// 累计良率
	private Float yieldRate;
	// 针孔缺陷数量
	private Integer pinNum;
	// 气泡缺陷数量
	private Integer bubbleNum;
	// 划痕缺陷数量
	private Integer scratchNum;
	// 锡灰缺陷数量
	private Integer tinAshNum;
}
