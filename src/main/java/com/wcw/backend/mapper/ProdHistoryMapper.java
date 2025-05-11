package com.wcw.backend.mapper;

import com.wcw.backend.entity.ProdHistoryData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 生产历史数据访问接口
 * 负责处理生产历史数据的数据库操作
 */
@Mapper
public interface ProdHistoryMapper {
	/**
	 * 查询所有生产历史数据
	 *
	 * @return 生产历史数据
	 */
	List<ProdHistoryData> findAll();

	/**
	 * 根据日期查询生产历史数据
	 * @param prodDate 日期
	 * @return 生产历史数据
	 */
	ProdHistoryData findByDate(String prodDate);

	/**
	 * 插入生产历史数据
	 * @param data 数据
	 */
	void insert(ProdHistoryData data);

	/**
	 * 更新生产历史数据
	 * @param data 数据
	 */
	void update(ProdHistoryData data);
}
