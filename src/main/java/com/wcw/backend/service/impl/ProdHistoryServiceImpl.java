package com.wcw.backend.service.impl;

import com.wcw.backend.entity.ProdHistoryData;
import com.wcw.backend.mapper.ProdHistoryMapper;
import com.wcw.backend.service.IProdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生产历史数据服务实现类
 * 提供生产历史数据的查询功能
 */
@Service
public class ProdHistoryServiceImpl implements IProdHistoryService {
	@Autowired
	private ProdHistoryMapper prodHistoryMapper;

	/**
	 * 查询所有生产历史数据
	 *
	 * @return 生产历史数据列表
	 */
	@Override
	public List<ProdHistoryData> findAll() {
		return prodHistoryMapper.findAll();
	}

	@Override
	public void saveOrUpdateToday(String prodDate, boolean hasDefect) {
		ProdHistoryData data = prodHistoryMapper.findByDate(prodDate);
		if (data == null) {
			// 新增
			ProdHistoryData newData = new ProdHistoryData();
			newData.setProdDate(prodDate);
			newData.setProdNum(1);
			newData.setDefectNum(hasDefect ? 1 : 0);
			prodHistoryMapper.insert(newData);
		} else {
			// 更新
			data.setProdNum(data.getProdNum() + 1);
			if (hasDefect) {
				data.setDefectNum(data.getDefectNum() + 1);
			}
			prodHistoryMapper.update(data);
		}
	}
}
