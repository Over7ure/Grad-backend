package com.wcw.backend.controller;

import com.wcw.backend.entity.OriginalPic;
import com.wcw.backend.service.IOriginalPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class OriginalPicController {
    @Autowired
    private IOriginalPicService originalPicService;

    @PostMapping("/saveList")
    public String saveList(@RequestBody List<OriginalPic> picList) {
        originalPicService.saveBatch(picList);
        return "success";
    }
}
