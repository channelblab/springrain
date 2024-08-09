package com.channelblab.springrain.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelUtil {


    //    public static List<LanguageTemplate> importExcel(MultipartFile file) throws IOException {
    //        List<LanguageTemplate> templates = new ArrayList<>();
    //
    //        try (InputStream inputStream = file.getInputStream()) {
    //            Workbook workbook = new XSSFWorkbook(inputStream);
    //            Sheet sheet = workbook.getSheetAt(0);
    //
    //            // 假设第一行是标题行，第二行是语言代码行，实际数据从第三行开始
    //            Row languageRow = sheet.getRow(2); // 第三行语言代码
    //
    //            for (int i = 3; i <= sheet.getLastRowNum(); i++) {  // 数据从第4行开始
    //                Row row = sheet.getRow(i);
    //                if (row == null) continue;
    //
    //                LanguageTemplate template = new LanguageTemplate();
    //                template.setIdentifier(row.getCell(0).getStringCellValue());
    //                template.setMeaning(row.getCell(1).getStringCellValue());
    //
    //                Map<String, String> translations = new HashMap<>();
    //                for (int j = 2; j < row.getLastCellNum(); j++) {
    //                    String langCode = languageRow.getCell(j).getStringCellValue();
    //                    String translation = row.getCell(j).getStringCellValue();
    //                    translations.put(langCode, translation);
    //                }
    //                template.setTranslations(translations);
    //
    //                templates.add(template);
    //            }
    //        }
    //
    //        return templates;
    //    }


    public static Workbook exportMultilingualExcel(Map<String, Object> dataForExport) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LanguageTemplate");
        // 设置每一列的宽度（单位是 1/256 个字符宽度）
        sheet.setColumnWidth(0, 30 * 256); // 设置第一列宽度为30个字符
        sheet.setColumnWidth(1, 30 * 256); // 设置第二列宽度为30个字符
        sheet.setColumnWidth(2, 30 * 256);
        sheet.setColumnWidth(3, 30 * 256);
        Map<String, String> frameData = (Map<String, String>) dataForExport.get("frameData");
        Set<String> frameKeys = frameData.keySet();

        // 创建标题行
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("多语言模板");
        titleCell.setCellStyle(createMergedCellStyle(workbook));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, frameKeys.size() + 1));

        Row rowTwo = sheet.createRow(1);
        if (frameKeys.size() != 1) {
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, frameKeys.size() + 1));
        }
        rowTwo.createCell(2).setCellValue("文案");

        Row rowThree = sheet.createRow(2);
        Row rowFour = sheet.createRow(3);

        int ki = 0;
        for (String frameKey : frameKeys) {
            rowThree.createCell(2 + ki).setCellValue(frameData.get(frameKey));
            rowFour.createCell(2 + ki).setCellValue(frameKey);
            ki++;
        }
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
        // 创建语言代码行
        rowTwo.createCell(0).setCellValue("标识");
        rowTwo.createCell(1).setCellValue("含义");

        List<Map<String, Object>> realDatas = (List<Map<String, Object>>) dataForExport.get("realData");
        int startRow = 4;
        for (Map<String, Object> realData : realDatas) {
            Row row = sheet.createRow(startRow++);
            row.createCell(0).setCellValue(realData.get("symbol") != null ? realData.get("symbol").toString() : null);
            row.createCell(1).setCellValue(realData.get("symbolDescribe") != null ? realData.get("symbolDescribe").toString() : null);
            int vi = 2;
            for (String frameKey : frameKeys) {
                row.createCell(vi++).setCellValue(realData.get(frameKey) != null ? realData.get(frameKey).toString() : null);
            }
        }
        return workbook;
    }

    private static CellStyle createMergedCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

}
