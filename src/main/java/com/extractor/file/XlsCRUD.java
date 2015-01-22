package com.extractor.file;

import com.extractor.model.Company;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Padonag on 09.11.2014.
 */
public interface XlsCRUD {

    public File createXlsFile(String name, List<Company> dataToFile);

    public List<Company> readCompaniesListFromXlsFile(FileInputStream fis);

    public boolean updateXlsFile(File existFile, List<Company> newData);

    public boolean deleteXlsFile(File file);
}
