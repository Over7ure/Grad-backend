package com.wcw.backend.controller;

import com.wcw.backend.entity.ProdHistoryData;
import com.wcw.backend.service.IProdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 生产历史数据控制器
 * 处理生产历史数据相关操作
 */
@RestController
@RequestMapping("/api/prodHistory")
public class ProdHistoryController {
	@Autowired
	private IProdHistoryService prodHistoryService;

	/**
	 * 查询所有生产历史数据
	 * @return 生产历史数据列表
	 */
	@RequestMapping("/findAll")
	public List<ProdHistoryData> findAll() {
		return prodHistoryService.findAll();
	}
}
