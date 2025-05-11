package com.wcw.backend.service;

import com.wcw.backend.entity.ProdHistoryData;

import java.util.List;

/**
 * 生产历史数据服务接口
 * 定义生产历史数据的查询功能
 */
public interface IProdHistoryService {
	/**
	 * 查询所有生产历史数据
	 *
	 * @return 生产历史数据列表
	 */
	List<ProdHistoryData> findAll();

	/**
	 * 保存或更新当日生产历史数据
	 * @param prodDate 日期
	 * @param hasDefect 是否检测到缺陷
	 */
	void saveOrUpdateToday(String prodDate, boolean hasDefect);
}
