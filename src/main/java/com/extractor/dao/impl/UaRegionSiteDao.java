package com.extractor.dao.impl;

import com.extractor.dao.UserProxy;
import com.extractor.model.Company;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Padonag on 08.11.2014.
 */
public class UaRegionSiteDao extends SiteDao {
    private static final String COMPANY_URL = "http://www.ua-region.info/";
    private static final String PAGE_SUFFIX = "?start_page=";

    public UaRegionSiteDao() {
        super();
    }

    @Override
    public String getPageSuffix() {
        return PAGE_SUFFIX;
    }

    @Override
    public List<Company> getCompanies(String webAddress, UserProxy userProxy) {
        if (webAddress.startsWith(COMPANY_URL)) {
            return parseSiteData(webAddress, userProxy);
        } else {
            JOptionPane.showMessageDialog(null, parsers.getString("not.this.site"));
            return Collections.EMPTY_LIST;
        }
    }


    private void getParsedCompany(Element dataTableElement, Company result) throws IOException {

        Elements data = dataTableElement.getElementsByClass("data");
        Elements label = dataTableElement.getElementsByClass("label");
        Map<String, String> companyMap = new HashMap<String, String>();
        if (data.size() == label.size()) {
            for (int i = 0; i < data.size(); i++) {
                companyMap.put(label.get(i).text(), data.get(i).text());
            }
        }

        for (Map.Entry<String, String> entry : companyMap.entrySet()) {
            if (parsers.getString("uaregion.physical.address").equals(entry.getKey()) || parsers.getString("uaregion.legal.address").equals(entry.getKey())) {
                result.setAddress(entry.getValue());
            } else if (parsers.getString("uaregion.phones").equals(entry.getKey())) {
                result.setPhoneNumber(entry.getValue());
            } else if (parsers.getString("uaregion.email").equals(entry.getKey())) {
                result.setEmail(entry.getValue());
            } else if (parsers.getString("uaregion.fax").equals(entry.getKey())) {
                result.setFax(entry.getValue());
            } else if (parsers.getString("uaregion.site").equals(entry.getKey())) {
                result.setSite(entry.getValue());
            }
        }
    }

    protected void parseHtml(Document document, List<Company> results, UserProxy userProxy) {
        try {
            Elements companiesList = document.select("div.company-info");
            Elements companyDataTables = companiesList.select("table.company-info-data-table");
            Elements numOfCompanies = document.select("div.b-items-total");
            String numberString = numOfCompanies.text();
            int numberPos = numberString.lastIndexOf(" ") + 1;
            String number = numberString.substring(numberPos, numberString.length());
            this.numberOfCompanies = Integer.parseInt(number);
            if (companiesList.size() == companyDataTables.size()) {
                for (int i = 0; i < companiesList.size(); i++) {
                    Element company = companiesList.get(i);
                    Element dataTable = companyDataTables.get(i);
                    /*0-th element - address
                    * 1-th element - phone(s)
                    * 2-th element - fax
                    * 3-th element - email*/

                    Company tempCompany = new Company();
                    tempCompany.setName(company.getElementsByTag("h2").text());
                    getParsedCompany(dataTable, tempCompany);
                    results.add(tempCompany);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
