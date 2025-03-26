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
	 * @return 生产历史数据列表
	 */
	List<ProdHistoryData> findAll();
}
