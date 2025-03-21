package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DetectResultInfo {
	private Integer testSn;
	private Integer picId;
	private Integer defectId;
	private Integer modelId;
	private String showStatus;
	private Date detectTime;
}