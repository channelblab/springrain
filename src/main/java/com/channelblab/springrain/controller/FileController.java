package com.channelblab.springrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.common.anotations.NoAuth;
import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.model.File;
import com.channelblab.springrain.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@Validated
@Tag(name = "框架-文件接口")
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;


    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public String[] upload(@RequestParam("files") @NotNull MultipartFile[] files) {
        return fileService.upload(files);
    }

    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public IPage<File> page(Integer page, Integer size, String name) {
        return fileService.page(page, size, name);
    }

    /**
     * we can control the file life time and view permission in this method
     * @param response
     */
    @NoLog
    @NoLogin
    @NoAuth
    @Operation(summary = "预览")
    @GetMapping("/preview")
    public void preview(HttpServletResponse response) {
    }


}
