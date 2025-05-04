package com.wcw.backend.mapper;

import com.wcw.backend.entity.OriginalPic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图片数据访问接口
 * 负责处理原始图片的数据库操作
 */
@Mapper
public interface ImageMapper {
	/**
	 * 保存图片信息到数据库
	 *
	 * @param originalPic 原始图片对象
	 */
	void save(OriginalPic originalPic);

	/**
	 * 查询所有图片信息
	 *
	 * @return 图片列表
	 */
	List<OriginalPic> findAll();

	/**
	 * 分页查询图片信息
	 *
	 * @param pageNum  页码偏移量
	 * @param pageSize 每页大小
	 * @param picName  图片名称（模糊查询条件）
	 * @return 分页的图片列表
	 */
	List<OriginalPic> selectPage(Integer pageNum, Integer pageSize, String picName);

	/**
	 * 获取符合条件的图片总数
	 *
	 * @param picName 图片名称（模糊查询条件）
	 * @return 图片总数
	 */
	Integer selectTotal(String picName);

	/**
	 * 根据ID删除图片
	 *
	 * @param picId 图片ID
	 * @return 删除成功返回true，否则返回false
	 */
	boolean deleteById(Integer picId);

	/**
	 * 根据ID查找图片
	 *
	 * @param picId 图片ID
	 * @return 图片对象
	 */
	OriginalPic findById(Integer picId);
}
