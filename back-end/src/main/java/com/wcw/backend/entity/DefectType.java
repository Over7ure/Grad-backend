package com.wcw.backend.entity;

import lombok.Data;

@Data
public class DefectType {
	private Integer defectId;
	private String defectName;
	private String defectDesc;
	private Integer isEnabled;
}