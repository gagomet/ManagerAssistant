package com.extractor.dao.impl;

import com.extractor.dao.UserProxy;
import com.extractor.model.Company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Padonag on 23.11.2014.
 */
@Service("catalog.ui.ua")
public class UiUaSiteDao extends SiteDao {
    private static final String COMPANY_URL = "http://catalog.ui.ua";
    private static final String CONTACTS_SUFFIX = "contacts";
    private final String PAGE_SUFFIX = "?page=";

    public UiUaSiteDao() {
        super();
    }

    public String getPageSuffix() {
        return PAGE_SUFFIX;
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
        Elements number = document.select("span.badge");
        String stringNumber = number.first().text();
        numberOfCompanies = Integer.parseInt(stringNumber.substring(1, stringNumber.length() - 1));
        List<String> companiesPages = getCompaniesUrlsFromPage(document);
        for (String tempCompanyPage : companiesPages) {
            try {
                Document companyPageDocument;
                if (userProxy == null) {
                    companyPageDocument = Jsoup.connect(tempCompanyPage).get();
                } else {
                    String tmp = getHtmlInString(tempCompanyPage, userProxy);
                    companyPageDocument = Jsoup.parse(tmp);
                }
                Elements contactsTable = companyPageDocument.select("table.table.table-striped");
                Company tempCompany = new Company();
                Elements rows = contactsTable.select("tr");
                /*0-th element - name
                    * 1-th element - phone(s)
                    * 2-th element - address
                    * 3-th element - email*/
                for (Element tempRow : rows) {
                    Elements columns = tempRow.select("td");

                    for (int i = 0; i < columns.size(); ++i) {
                        if (parsers.getString("uiua.name").equals(columns.get(i).text())) {
                            if (isEmptyString(columns.get(i + 1).text())) {
                                tempCompany.setName(Company.UNDEFINED);
                            } else {
                                tempCompany.setName(columns.get(i + 1).text());
                            }
                        } else if (parsers.getString("uiua.phones").equals(columns.get(i).text())) {
                            if (isEmptyString(columns.get(i + 1).text())) {
                                tempCompany.setPhoneNumber(Company.UNDEFINED);
                            } else {
                                tempCompany.setPhoneNumber(columns.get(i + 1).text());
                            }
                        } else if (parsers.getString("uiua.address").equals(columns.get(i).text())) {
                            if (isEmptyString(columns.get(i + 1).text())) {
                                tempCompany.setAddress(Company.UNDEFINED);
                            } else {
                                tempCompany.setAddress(columns.get(i + 1).text());
                            }
                        } else if (parsers.getString("uiua.email").equals(columns.get(i).text())) {
                            if (isEmptyString(columns.get(i + 1).text())) {
                                tempCompany.setEmail(Company.UNDEFINED);
                            } else {
                                tempCompany.setEmail(columns.get(i + 1).text());
                            }
                        }
                    }
                }
                results.add(tempCompany);
                System.out.println(" ");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private List<String> getCompaniesUrlsFromPage(Document document) {
        List<String> results = new ArrayList<String>();
        Elements companiesPages = document.select("a.cat-company-link");
        for (int i = 0; i < companiesPages.size(); ++i) {
            results.add(companiesPages.get(i).attr("abs:href") + CONTACTS_SUFFIX);
        }
        return results;
    }
}
