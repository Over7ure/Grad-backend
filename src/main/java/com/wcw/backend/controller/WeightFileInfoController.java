package com.wcw.backend.controller;

import com.wcw.backend.entity.WeightFileInfo;
import com.wcw.backend.service.IWeightFileInfoService;
import com.wcw.backend.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weight")
public class WeightFileInfoController {
    @Autowired
    private IWeightFileInfoService weightFileInfoService;

    @PostMapping("/save")
    public Result saveWeight(@RequestBody Map<String, String> requestBody) {
        String weightFileId = requestBody.get("weightFileId");
        int epoch = Integer.parseInt(requestBody.get("epoch"));

        if (weightFileId == null || epoch <= 0) {
            return new Result(400, "Missing required parameters");
        }

        WeightFileInfo weight = new WeightFileInfo();
        weight.setWeightFileId(weightFileId);
        weight.setEpoch(epoch);

        return weightFileInfoService.uploadWeightFile(weight)
            ? new Result(200, "Weight file uploaded successfully")
            : new Result(500, "Failed to upload weight file");
    }

    @GetMapping("/findAll")
    public List<WeightFileInfo> findAll() {
        return weightFileInfoService.findAll();
    }

}
