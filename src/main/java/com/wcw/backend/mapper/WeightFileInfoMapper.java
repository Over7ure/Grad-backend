package com.wcw.backend.mapper;

import com.wcw.backend.entity.WeightFileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface WeightFileInfoMapper {
    boolean save(WeightFileInfo weightFileInfo);
    List<WeightFileInfo> findAll();
}
