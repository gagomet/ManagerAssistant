package com.extractor.dao;

import com.extractor.model.Company;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Padonag on 09.11.2014.
 */
@Service("companydao")
public interface ICompaniesDAO {
    public List<Company> getCompanies(String address, UserProxy userProxy);
    public int getNumberOfCompanies();
    public int getNumberCompaniesOnPage();
    public int getNumberOfpages();
    public String getPageSuffix();
}
