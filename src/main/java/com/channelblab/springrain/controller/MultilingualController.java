package com.channelblab.springrain.controller;

import com.channelblab.springrain.common.anotations.NoResponseHandle;
import com.channelblab.springrain.model.MultilingualDto;
import com.channelblab.springrain.service.MultilingualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-18 13:11
 * @description：
 * @modified By：
 */
@Tag(name = "框架-多语言接口")
@RestController
@RequestMapping("/multilingual")
public class MultilingualController {
    @Autowired
    private MultilingualService multilingualService;


    @Operation(summary = "导出Excel")
    @NoResponseHandle
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.getWriter().write("");
    }

    @Operation(summary = "导入多语言Excel")
    @GetMapping("/importExcel")
    public void importExcel(HttpServletResponse response) throws IOException {
        response.getWriter().write("");
    }

    @Operation(summary = "查询所有多语言数据")
    @GetMapping
    public MultilingualDto allLang() {
        return multilingualService.allLang();
    }


}
