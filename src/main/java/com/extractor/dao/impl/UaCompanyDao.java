package com.extractor.dao.impl;

import com.extractor.dao.UserProxy;
import com.extractor.model.Company;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.swing.JOptionPane;
import java.util.Collections;
import java.util.List;

/**
 * Created by Padonag on 04.12.2014.
 */
@Service("ua-company-dao")
public class UaCompanyDao extends SiteDao {
    private static final String COMPANY_URL = "http://www.ua-company.com/";
    private static final String PAGE_SUFFIX = "?page=";

    public UaCompanyDao() {
        super();
    }

    @Override
    public List<Company> getCompanies(String address, UserProxy userProxy) {
        if (address.startsWith(COMPANY_URL)) {
            return parseSiteData(address, userProxy);
        } else {
            JOptionPane.showMessageDialog(null, parsers.getString("not.this.site"));
            return Collections.EMPTY_LIST;
        }
    }


    protected void parseHtml(Document document, List<Company> results, UserProxy userProxy) {
        numberOfCompanies = getCompaniesNumberFromPage(document);
        Elements companies = document.select("ul.topCompanys");
        Elements companiesList = companies.select("li.topCompanys-item");
        for (Element tempCompany : companiesList) {
            Company newCompany = new Company();
            Elements tempCompanyTxt = tempCompany.select("div.topCompanys-txt");
            String name = null;
            if (tempCompanyTxt.select("a") != null) {
                name = tempCompanyTxt.select("a").first().text();
            }
            newCompany.setName(name);
            Elements companyCoords = tempCompanyTxt.select("p.adrs");
            /*0 - address
            * 1 - phone
            * 2 - email
            * 3 - site*/
            int coordsSize = companyCoords.size();
            for (int i = 0; i < coordsSize; i++) {
                if (i == 0) {
                    newCompany.setAddress(companyCoords.get(i).text());
                } else if (companyCoords.get(i).text().contains(parsers.getString("ua.company.phone"))) {
                    newCompany.setPhoneNumber(companyCoords.get(i).text());
                } else if (companyCoords.get(i).text().contains(parsers.getString("ua.company.email"))) {
                    newCompany.setEmail(getStringBeforeSeparator(companyCoords.get(i).text(), ' ', false));
                } else if (companyCoords.get(i).text().contains(parsers.getString("ua.company.site"))) {
                    newCompany.setSite(getStringBeforeSeparator(companyCoords.get(i).text(), ' ', false));
                }
            }
            results.add(newCompany);
        }

    }

    @Override
    public String getPageSuffix() {
        return PAGE_SUFFIX;
    }

    private int getCompaniesNumberFromPage(Document document){
        String number = document.select("div.categories-title-s").first().text();
        return Integer.parseInt(getStringBeforeSeparator(number, ' ', true));
    }
}
