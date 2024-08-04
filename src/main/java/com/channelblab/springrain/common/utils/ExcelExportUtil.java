package com.channelblab.springrain.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelExportUtil {

    private static final int ROW_ACCESS_WINDOW_SIZE = 100;

    public static <T> void exportExcel(List<T> data, List<String> headers, List<CellValueExtractor<T>> extractors, OutputStream outputStream) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE)) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(headers.get(i));
            }

            // Create data rows
            int rowNum = 1;
            for (T item : data) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < extractors.size(); i++) {
                    Cell cell = row.createCell(i);
                    extractors.get(i).extractValue(item, cell);
                }
            }

            workbook.write(outputStream);
        }
    }

    @FunctionalInterface
    public interface CellValueExtractor<T> {
        void extractValue(T item, Cell cell);
    }
}
