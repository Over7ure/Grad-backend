package com.wcw.backend.service;

import com.wcw.backend.entity.OriginalPic;
import java.util.List;

public interface IOriginalPicService {
    void saveBatch(List<OriginalPic> picList);
}
