package com.wcw.backend.controller;

import com.wcw.backend.entity.ProdHistoryData;
import com.wcw.backend.service.IProdHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProdHistoryControllerTest {

    @Mock
    private IProdHistoryService prodHistoryService;

    @InjectMocks
    private ProdHistoryController prodHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        // 准备测试数据
        List<ProdHistoryData> mockDataList = new ArrayList<>();
        ProdHistoryData data1 = new ProdHistoryData();
        data1.setProdDate("2023-01-01");
        data1.setProdNum(100);
        data1.setCurDefectNum(5);
        data1.setCurYieldRate(0.95f);

        ProdHistoryData data2 = new ProdHistoryData();
        data2.setProdDate("2023-01-02");
        data2.setProdNum(120);
        data2.setCurDefectNum(3);
        data2.setCurYieldRate(0.975f);

        mockDataList.add(data1);
        mockDataList.add(data2);

        // 模拟service层的返回
        when(prodHistoryService.findAll()).thenReturn(mockDataList);
        
        // 调用控制器方法
        List<ProdHistoryData> result = prodHistoryController.findAll();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("2023-01-01", result.get(0).getProdDate());
        assertEquals(100, result.get(0).getProdNum());
        assertEquals("2023-01-02", result.get(1).getProdDate());
        assertEquals(120, result.get(1).getProdNum());
        
        // 验证service方法被调用
        verify(prodHistoryService, times(1)).findAll();
    }
}
