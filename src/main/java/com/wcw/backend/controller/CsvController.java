package com.wcw.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/csv")
public class CsvController {
    @GetMapping("/data_record")
    public ResponseEntity<String> getCsvContent() {
        try {
            String path = "docker_files/data_record.csv";
            String content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("读取csv失败: " + e.getMessage());
        }
    }
}
