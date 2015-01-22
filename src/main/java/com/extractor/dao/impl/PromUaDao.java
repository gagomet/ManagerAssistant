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
 * Created by Padonag on 30.11.2014.
 */
@Service("prom-ua-dao")
public class PromUaDao extends SiteDao {
    private static final String COMPANY_URL = "http://prom.ua/";

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
        return null;
    }


    protected void parseHtml(Document document, List<Company> results, UserProxy userProxy) {
        Elements jsons = document.getElementsByAttribute("data-cn-info");
        List<String> companiesUrls = getCompaniesUrls(jsons);
        for (String tempCompanyUrl : companiesUrls) {
            Company tempCompany = new Company();
            try {
                Document companyPageDocument;
                if (userProxy == null) {
                    companyPageDocument = Jsoup.connect(tempCompanyUrl).get();
                } else {
                    String tmp = getHtmlInString(tempCompanyUrl, userProxy);
                    companyPageDocument = Jsoup.parse(tmp);
                }
                Elements contacts = companyPageDocument.getElementsByClass("b-contact-info__row");
                for (Element currentElement : contacts) {
                    if (currentElement.attr("title").equals(parsers.getString("prom.ua.name"))) {
                        System.out.println(currentElement.text());
                        tempCompany.setName(currentElement.text());
                    } else if (currentElement.attr("title").equals(parsers.getString("prom.ua.phone"))) {
                        System.out.println(currentElement.text());
                        tempCompany.setPhoneNumber(currentElement.text());
                    } else if (currentElement.attr("title").equals(parsers.getString("prom.ua.address"))) {
                        Elements address = currentElement.getElementsByClass("b-contact-info__comma");
                        StringBuilder builder = new StringBuilder();
                        for (Element partOfAddress : address) {
                            builder.append(partOfAddress.text());
                            builder.append(", ");
                        }
                        System.out.println(builder.toString());
                        tempCompany.setAddress(builder.toString());
                    } else if (currentElement.attr("title").equals(parsers.getString("prom.ua.email"))) {
                        System.out.println(currentElement.text());
                        tempCompany.setEmail(currentElement.text());
                    } else if (currentElement.attr("title").equals(parsers.getString("prom.ua.site"))) {
                        System.out.println(currentElement.text());
                        tempCompany.setSite(currentElement.text());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            results.add(tempCompany);
        }

    }

    private List<String> getCompaniesUrls(Elements elements) {
        List<String> urls = new ArrayList<String>();
        for (org.jsoup.nodes.Element element : elements) {
            String attributes = element.attr("data-cn-info");
            String[] splitList = attributes.split("\"");
            for (int i = 0; i < splitList.length - 2; i++) {
                if (splitList[i].contains("contacts_url")) {
                    urls.add(splitList[i + 2]);
                }
            }
        }
        System.out.println("   ");
        return urls;
    }

    private Company getInternetMarketNoPage(String marketUrl, UserProxy userProxy) {
        Company result = new Company();
        try {
            Document companyDocument;
            if (userProxy == null) {
                companyDocument = Jsoup.connect(marketUrl).get();
            } else {
                String tmp = getHtmlInString(marketUrl, userProxy);
                companyDocument = Jsoup.parse(tmp);
            }
//            Elements
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
