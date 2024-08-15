package com.channelblab.springrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.common.anotations.NoResponseHandle;
import com.channelblab.springrain.common.utils.ExcelUtil;
import com.channelblab.springrain.model.Permission;
import com.channelblab.springrain.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 权限点
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：
 * @modified By：
 */
@Tag(name = "框架-权限接口")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    //todo 没有分页查的必要
    @NoLog
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public IPage<Permission> page(Integer page, Integer size, String name) {
        return permissionService.page(page, size, name);
    }


    @NoLog
    @Operation(summary = "权限树")
    @GetMapping("/tree")
    public List<Permission> tree() {
        return permissionService.tree();
    }


    @NoLog
    @Operation(summary = "导出Excel")
    @NoResponseHandle
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("权限信息.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        Workbook workbook = ExcelUtil.exportPermissionExcel(tree());
        workbook.write(response.getOutputStream());
    }

    @NoLog
    @Operation(summary = "导入Excel")
    @PostMapping("/importExcel")
    public void importExcel(@RequestParam @NotNull MultipartFile file) throws IOException {
        List<Permission> permissions = ExcelUtil.resolvePermission(file.getInputStream());
        permissionService.fullyUpdate(permissions);
    }

}
