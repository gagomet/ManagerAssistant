package com.extractor.dao.impl;

import com.extractor.dao.ICompaniesDAO;
import com.extractor.dao.UserProxy;
import com.extractor.model.Company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 24.11.2014.
 */
public abstract class SiteDao implements ICompaniesDAO {
    private static final String EMPTY_STRING = "";
    protected ResourceBundle parsers = ResourceBundle.getBundle("parsers");
    protected int numberOfCompanies = 0;
    protected int numberCompaniesOnPage = 0;
    protected int numberOfpages = 0;

    @Override
    public int getNumberOfpages() {
        return numberOfpages;
    }

    @Override
    public int getNumberOfCompanies() {
        return numberOfCompanies;
    }

    @Override
    public int getNumberCompaniesOnPage() {
        return numberCompaniesOnPage;
    }

    protected String getHtmlInString(String url, UserProxy userProxy) {
        StringBuffer tmp = new StringBuffer();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(userProxy.getProxyHost(), userProxy.getProxyPort()));
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.connect();
            String line = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(tmp);
    }

    protected boolean isEmptyString(String string) {
        if (EMPTY_STRING.equals(string)) {
            return true;
        }
        return false;
    }

    protected String getStringBeforeSeparator(String inputString, char separator, boolean isBefore) {
        int separatorPosition = inputString.indexOf(separator);
        if (isBefore) {
            return inputString.substring(0, separatorPosition);
        } else {
            return inputString.substring(separatorPosition + 1, inputString.length());
        }
    }

    protected List<Company> parseSiteData(String url, UserProxy userProxy) {
        List<Company> results = new ArrayList<Company>();
        Document document;
        try {
            if (userProxy != null) {
                String tmp = getHtmlInString(url, userProxy);
                document = Jsoup.parse(tmp);
                parseHtml(document, results, userProxy);
            } else {
                document = Jsoup.connect(url).get();
                parseHtml(document, results, null);
            }

        } catch (SocketTimeoutException e) {
            System.out.println("Socket Timeout Exception" + e.toString());
        } catch (java.net.UnknownHostException e) {
            JOptionPane.showMessageDialog(null, parsers.getString("no.network"));
        } catch (IOException e) {
            e.printStackTrace();

        }
        numberCompaniesOnPage = results.size();
        if (numberCompaniesOnPage != 0) {
            numberOfpages = (numberOfCompanies + (numberCompaniesOnPage - 1)) / numberCompaniesOnPage;
        }
        return results;
    }

    protected void parseHtml(Document document, List<Company> results, UserProxy userProxy) {

    }


}
