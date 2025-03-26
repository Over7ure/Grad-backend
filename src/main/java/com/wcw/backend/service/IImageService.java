package com.wcw.backend.service;

import com.wcw.backend.entity.OriginalPic;

import java.util.List;
import java.util.Map;

/**
 * 图片服务接口
 * 定义图片上传、查询和删除等相关操作
 */
public interface IImageService {
	/**
	 * 手动上传图片
	 * @param picUrl 图片的Base64编码字符串
	 * @param pinName 图片名称
	 * @return 上传是否成功
	 */
	boolean uploadImage(String picUrl, String pinName);

	/**
	 * 查询所有图片
	 * @return 图片列表
	 */
	List<OriginalPic> findAll();

	/**
	 * 分页查询图片
	 * @param pageNum 页码
	 * @param pageSize 每页数量
	 * @param picName 图片名称（模糊查询）
	 * @return 包含数据和总数的Map
	 */
	Map<String, Object> findPage(Integer pageNum, Integer pageSize, String picName);

	/**
	 * 根据ID删除图片
	 * @param picId 图片ID
	 * @return 删除是否成功
	 */
	boolean deleteById(Integer picId);
}
