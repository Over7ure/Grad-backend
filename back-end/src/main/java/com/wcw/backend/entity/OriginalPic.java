package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OriginalPic {
	private Integer picId;
	private String picName;
	private String picUrl;
	private String fileMd5;
	private Integer prodLineId;
	private Date uploadDate;
	private Integer isPublic;
}