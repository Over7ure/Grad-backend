package com.wcw.backend.service.impl;

import com.wcw.backend.entity.OriginalPic;
import com.wcw.backend.mapper.ImageMapper;
import com.wcw.backend.service.IOriginalPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OriginalPicServiceImpl implements IOriginalPicService {
    @Autowired
    private ImageMapper imageMapper;

    @Override
    public void saveBatch(List<OriginalPic> picList) {
        for (OriginalPic pic : picList) {
            imageMapper.save(pic);
        }
    }
}
