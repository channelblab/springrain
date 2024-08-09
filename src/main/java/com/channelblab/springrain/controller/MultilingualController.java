package com.channelblab.springrain.controller;

import com.channelblab.springrain.common.anotations.NoAuth;
import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.common.anotations.NoResponseHandle;
import com.channelblab.springrain.common.utils.ExcelUtil;
import com.channelblab.springrain.model.Multilingual;
import com.channelblab.springrain.service.MultilingualService;
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
import java.util.Map;

/**
 * 复杂的数据利用excel导入导出处理就可以了，没必要在页面上过多处理
 *
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
        Map<String, Object> dataForExport = multilingualService.dataForExport();

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("LanguageTemplate.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        try (Workbook workbook = ExcelUtil.exportMultilingualExcel(dataForExport)) {

            workbook.write(response.getOutputStream());
        }


    }

    @Operation(summary = "导入多语言Excel")
    @PostMapping("/importExcel")
    public void importExcel(@RequestParam @NotNull MultipartFile file) {
        multilingualService.importExcel(file);
    }


    @Operation(summary = "查询所有多语言数据")
    @GetMapping
    public List<Map<String, Object>> allLang() {
        return multilingualService.allLang();
    }

    @NoLogin
    @NoAuth
    @Operation(summary = "语言列表")
    @GetMapping("/langList")
    public List<Multilingual> langList() {
        return multilingualService.langList();
    }

    @Operation(summary = "新增或修改多语言")
    @PostMapping("/addOrUpdate")
    public void addOrUpdate(@RequestBody Multilingual multilingual) {
        multilingualService.addOrUpdate(multilingual);
    }


}
