package com.extractor.service;

import com.extractor.dao.UserProxy;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Padonag on 06.11.2014.
 */
public class EmailCrawler {

    private static final String EMAIL_REGEX = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private static final String NO_NET = "Sorry! Unable to connect to Internet. Check your settings!";

    private EmailCrawler() {
    }

    public static List<String> getEmails(URL url, UserProxy userProxy) {
        return getEmailsFromUrl(url, userProxy);
    }

    private static List<String> getEmailsFromUrl(URL url, UserProxy userProxy) {
        List<String> results = new ArrayList<String>();
        try {
            if (userProxy != null) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(userProxy.getProxyHost(), userProxy.getProxyPort()));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
                connection.connect();
//                StringBuffer tmp = new StringBuffer();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                crawl(in, results);
                connection.disconnect();
            } else {

                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                crawl(reader, results);
            }
        } catch (java.net.UnknownHostException e) {
            JOptionPane.showMessageDialog(null, NO_NET);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return results;
    }

    private static void crawl(BufferedReader reader, List<String> results) {
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile(EMAIL_REGEX);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    System.out.println("got it!" + matcher.group());
                    System.out.println(matcher.groupCount());
                    results.add(matcher.group(0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
