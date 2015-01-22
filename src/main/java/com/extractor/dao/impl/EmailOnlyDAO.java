package com.extractor.dao.impl;

import com.extractor.dao.ICompaniesDAO;
import com.extractor.dao.UserProxy;
import com.extractor.model.Company;
import com.extractor.service.EmailCrawler;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 07.11.2014.
 */
@Service("email-dao")
public class EmailOnlyDAO implements ICompaniesDAO {
    private final String PAGE_SUFFIX = "";
    private int numberOfCompanies = 0;
    private int numberCompaniesOnPage = 0;
    private int numberOfpages = 0;

    @Override
    public String getPageSuffix() {
        return PAGE_SUFFIX;
    }

    @Override
    public int getNumberOfCompanies() {
        return numberOfCompanies;
    }

    @Override
    public int getNumberCompaniesOnPage() {
        return numberCompaniesOnPage;
    }

    @Override
    public int getNumberOfpages() {
        return numberOfpages;
    }

    public List<Company> getCompanies(String address, UserProxy userProxy) {
        List<Company> result = new ArrayList<Company>();
        try {

            List<String> emails = getEmails(address, userProxy);
            for (String tempEmail : emails) {
                Company tempCompany = new Company();
                tempCompany.setEmail(tempEmail);
                result.add(tempCompany);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.numberOfCompanies = result.size();
        numberCompaniesOnPage = result.size();
        if (numberCompaniesOnPage != 0) {
            numberOfpages = (numberOfCompanies + (numberCompaniesOnPage - 1)) / numberCompaniesOnPage;
        }
        return result;
    }

    private List<String> getEmails(String url, UserProxy userProxy) throws MalformedURLException {
        return EmailCrawler.getEmails(stringToUrl(url), userProxy);
    }

    private URL stringToUrl(String address) throws MalformedURLException {
        return new URL(address);
    }
}
