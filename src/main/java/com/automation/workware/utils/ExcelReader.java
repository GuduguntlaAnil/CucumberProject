package com.automation.workware.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * ExcelReader is a lightweight helper to read Excel (.xls/.xlsx) data for data-driven tests.
 *
 * Expectations:
 * - The first row (row index 0) in each sheet is treated as a header row containing column names.
 * - Data rows start from row 1.
 *
 * Example usage:
 *   ExcelReader er = new ExcelReader("src/test/resources/testdata/testdata.xlsx");
 *   Map<String,String> row = er.getRowDataAsMap("Sheet1", 1); // get data from second row (index 1)
 *   List<Map<String,String>> all = er.getAllData("Sheet1");
 */
public class ExcelReader {
    Workbook workbook;

    public ExcelReader(String excelPath) {
        try {
            FileInputStream file = new FileInputStream(new File(excelPath));
            workbook = WorkbookFactory.create(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Low-level accessor by sheet name, zero-based row and column indices.
     */
    public String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);
        return cell.toString();
    }

    /** Returns last row number (zero-based) for the sheet. */
    public int getRowCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum();
    }

    // --- New helper methods for data-driven tests ---

    /**
     * Returns a map of header -> cellValue for the requested row.
     * Assumes first row (row 0) contains headers.
     * rowNum is zero-based (0 is the header row).
     */
    public Map<String, String> getRowDataAsMap(String sheetName, int rowNum) {
        Map<String, String> data = new HashMap<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return data;

        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(rowNum);
        if (headerRow == null || dataRow == null) return data;

        int lastCell = headerRow.getLastCellNum();
        for (int c = 0; c < lastCell; c++) {
            Cell headerCell = headerRow.getCell(c);
            Cell valueCell = dataRow.getCell(c);
            String header = headerCell == null ? "" : headerCell.toString().trim();
            String value = valueCell == null ? "" : valueCell.toString();
            if (!header.isEmpty()) {
                data.put(header, value);
            }
        }
        return data;
    }

    /**
     * Convenience method to get cell value by column name (header) for a given row.
     * Returns empty string if not found.
     */
    public String getCellData(String sheetName, String colName, int rowNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return "";
        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(rowNum);
        if (headerRow == null || dataRow == null) return "";

        int lastCell = headerRow.getLastCellNum();
        for (int c = 0; c < lastCell; c++) {
            Cell headerCell = headerRow.getCell(c);
            String header = headerCell == null ? "" : headerCell.toString().trim();
            if (header.equalsIgnoreCase(colName)) {
                Cell valueCell = dataRow.getCell(c);
                return valueCell == null ? "" : valueCell.toString();
            }
        }
        return "";
    }

    /**
     * Returns all rows (from row 1 to last) as a list of maps (header->value).
     * Useful to supply test data for data providers.
     */
    public List<Map<String, String>> getAllData(String sheetName) {
        List<Map<String, String>> rows = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return rows;

        int lastRow = sheet.getLastRowNum();
        // start from 1 to skip header
        for (int r = 1; r <= lastRow; r++) {
            Map<String, String> rowMap = getRowDataAsMap(sheetName, r);
            if (!rowMap.isEmpty()) rows.add(rowMap);
        }
        return rows;
    }
}