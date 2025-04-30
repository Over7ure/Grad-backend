package com.wcw.backend.service;

import com.wcw.backend.entity.WeightFileInfo;
import java.util.List;

public interface IWeightFileInfoService {
    boolean uploadWeightFile(WeightFileInfo weightFileInfo);
    List<WeightFileInfo> findAll();
}
