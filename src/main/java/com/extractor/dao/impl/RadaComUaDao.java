package com.extractor.dao.impl;

import com.extractor.dao.UserProxy;
import com.extractor.model.Company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Padonag on 24.11.2014.
 */
@Service("rada-dao")
public class RadaComUaDao extends SiteDao {
    private static final String COMPANY_URL = "http://www.rada.com.ua/";
    private final String PAGE_SUFFIX = "?&p=";

    public RadaComUaDao() {
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

    @Override
    public String getPageSuffix() {
        return PAGE_SUFFIX;
    }

    protected void parseHtml(Document document, List<Company> results, UserProxy userProxy) {
        List<String> companiesUrls = getCompaniesUrlsFromPage(document);
        for (String tempCompanyPage : companiesUrls) {
            try {
                Document companyPageDocument;
                if (userProxy == null) {
                    companyPageDocument = Jsoup.connect(tempCompanyPage).get();
                } else {
                    String tmp = getHtmlInString(tempCompanyPage, userProxy);
                    companyPageDocument = Jsoup.parse(tmp);
                }
                Company tempCompany = new Company();
                Elements title = companyPageDocument.getElementsByTag("title");
                tempCompany.setName(getStringBeforeSeparator(title.first().text(), '|', true));
                Elements companyAttributes = companyPageDocument.getElementsByAttributeValue("id", "company_data_container");
                Elements lis = companyAttributes.select("li");
                /*0 - address
                * 1 - phone
                * 2 - website (may be null)*/
                tempCompany.setAddress(getStringBeforeSeparator(lis.get(0).text(), ':', false));
                tempCompany.setPhoneNumber(getStringBeforeSeparator(lis.get(1).text(), ':', false));
                if (lis.size() > 2) {
                    tempCompany.setSite(getStringBeforeSeparator(lis.get(2).text(), ':', false));
                }
                results.add(tempCompany);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private List<String> getCompaniesUrlsFromPage(Document document) {
        List<String> results = new ArrayList<String>();
        Elements companyList = document.getElementsByAttributeValue("id", "company_list");
        Elements companiesATags = companyList.select("a");
        for (int i = 0; i < companiesATags.size(); ++i) {
            results.add(companiesATags.get(i).attr("abs:href"));
        }
        numberOfCompanies = getCompaniesNumberInTheField(document);
        return results;
    }

    private int getCompaniesNumberInTheField(Document document) {
        String stringNode = null;
        Elements numberString = document.select("div.columns_padding");
        List<Node> ln = numberString.first().childNodes();
        try {
            stringNode = ln.get(8).toString();
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
        int lastSpace = stringNode.lastIndexOf(":");
        String number = stringNode.substring(lastSpace + 2, stringNode.length() - 1);
        return Integer.parseInt(number);
    }
}
