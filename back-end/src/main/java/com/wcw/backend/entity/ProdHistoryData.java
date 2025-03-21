package com.wcw.backend.entity;

import lombok.Data;

@Data
public class ProdHistoryData {
	private String prodDate;
	private Integer prodNum;
	private Integer curDefectNum;
	private Float curYieldRate;
	private Integer totalNum;
	private Integer defectNum;
	private Float yieldRate;
	private Integer pinNum;
	private Integer bubbleNum;
	private Integer scratchNum;
	private Integer tinAshNum;
}