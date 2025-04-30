package com.wcw.backend.service.impl;

import com.wcw.backend.entity.WeightFileInfo;
import com.wcw.backend.mapper.WeightFileInfoMapper;
import com.wcw.backend.service.IWeightFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightFileInfoServiceImpl implements IWeightFileInfoService {
    @Autowired
    private WeightFileInfoMapper weightFileInfoMapper;

    @Override
    public boolean uploadWeightFile(WeightFileInfo weightFileInfo) {
        return weightFileInfoMapper.save(weightFileInfo);
    }

    @Override
    public List<WeightFileInfo> findAll() {
        return weightFileInfoMapper.findAll();
    }

}
