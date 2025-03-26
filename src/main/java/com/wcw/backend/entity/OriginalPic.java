package com.wcw.backend.entity;

import lombok.Data;

import java.util.Date;

/**
 * 原始图片实体类
 * 存储上传的待检测图片信息
 */
@Data
public class OriginalPic {
	// 图片ID，唯一标识
	private Integer picId;
	// 图片名称
	private String picName;
	// 图片存储路径
	private String picUrl;
	// 上传日期
	private Date uploadDate;
}
