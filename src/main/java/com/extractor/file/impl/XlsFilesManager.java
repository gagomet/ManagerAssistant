package com.extractor.file.impl;

import com.extractor.file.XlsCRUD;
import com.extractor.model.Company;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Padonag on 09.11.2014.
 */
public class XlsFilesManager implements XlsCRUD {
    private static final Logger log = Logger.getLogger(XlsFilesManager.class);
    private static final String EMPTY_STRING = "";
    private static final String XLS_EXTENSION = "xls";
    private static final String DEFAULT_NAME = "CompaniesList";
    private static final Object[] COLUMN_NAMES = {"Company Name", "Address", "Phone", "Fax", "Email"};

    @Override
    public File createXlsFile(String absolutePath, List<Company> dataToFile) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(DEFAULT_NAME);
        Map<String, Object[]> dataMap = createDataMap(dataToFile, true);
        fillXlsSheetWithStringData(dataMap, sheet, 0);
        File outputFile = new File(absolutePath + "." + XLS_EXTENSION);
        if (outputFile.exists()) {
            System.out.println("File already exist!!!");
            return null;
        }
        writeWorkbookToFile(outputFile, workbook);
        return outputFile;
    }

    @Override
    public List<Company> readCompaniesListFromXlsFile(FileInputStream fis) {
        return getDataFromXlsFile(fis);
    }

    @Override
    public boolean updateXlsFile(File existFile, List<Company> newData) {
        try {
            FileInputStream fis = new FileInputStream(existFile);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Map<String, Object[]> dataMap = createDataMap(newData, false);
            fillXlsSheetWithStringData(dataMap, sheet, sheet.getLastRowNum());
            writeWorkbookToFile(existFile, workbook);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteXlsFile(File file) {
        if (file.delete()) {
            System.out.println("File deleted!");
            return true;
        }
        return false;
    }

    private Map<String, Object[]> createDataMap(List<Company> dataList, boolean isNewMap) {
        if (!dataList.isEmpty()) {
            Map<String, Object[]> result = new HashMap<String, Object[]>();
            if (isNewMap) {
                result.put("0", COLUMN_NAMES);
            }
            for (int i = 0; i < dataList.size(); i++) {
                Company tempCompany = dataList.get(i);
                List<Object> objectList = new LinkedList<Object>();
                objectList.add(tempCompany.getName());
                objectList.add(tempCompany.getAddress());
                objectList.add(tempCompany.getPhoneNumber());
                objectList.add(tempCompany.getFax());
                objectList.add(tempCompany.getEmail());
                result.put(Integer.toString(i + 2), objectList.toArray());
            }
            return result;
        }
        return Collections.EMPTY_MAP;
    }

    private void fillXlsSheetWithStringData(Map<String, Object[]> dataMap, HSSFSheet sheet, int rowNumber) {
        Set<String> keySet = dataMap.keySet();
        int rowNum = rowNumber;
        for (String key : keySet) {
            Row row = sheet.createRow(rowNum++);
            Object[] rowData = dataMap.get(key);
            int cellNum = 0;
            for (Object tempObject : rowData) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue((String) tempObject);
            }
        }
    }

    private void writeWorkbookToFile(File file, HSSFWorkbook workbook) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            log.debug("Throwing Exception", e);
        } catch (IOException e) {
            log.debug("Throwing Exception", e);
        }
    }

    private List<Company> getDataFromXlsFile(FileInputStream fis) {
        List<Company> results = new LinkedList<Company>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
                HSSFRow tempRow = sheet.getRow(i);
                Object[] data = parseRow(tempRow);
                Company tempCompany = new Company();
                tempCompany.setName((String) data[0]);
                tempCompany.setAddress((String) data[1]);
                tempCompany.setPhoneNumber((String) data[2]);
                tempCompany.setFax((String) data[3]);
                tempCompany.setEmail((String) data[4]);
                results.add(tempCompany);
            }
        } catch (FileNotFoundException e) {
            log.debug("Throwing Exception", e);
        } catch (IOException e) {
            log.debug("Throwing Exception", e);
        }
        return results;
    }

    private Object[] parseRow(HSSFRow row) {
        List<String> result = new LinkedList<String>();
        short rowSize = row.getLastCellNum();
        for (int i = 0; i < rowSize; ++i) {
            HSSFCell cell = row.getCell(i);
            String value;
            try {
                value = cell.getStringCellValue();
            } catch (NullPointerException e) {
                result.add(EMPTY_STRING);
                continue;
            }
            result.add(cell.getStringCellValue());
            System.out.println("  ");
        }
        return result.toArray();
    }


}
