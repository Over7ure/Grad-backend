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
	 * @return 生产历史数据
	 */
	List<ProdHistoryData> findAll();
}
