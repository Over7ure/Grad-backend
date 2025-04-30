package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WeightFileInfo {
	private String weightFileId;
	private Integer epoch;
	private Date createTime;
}
