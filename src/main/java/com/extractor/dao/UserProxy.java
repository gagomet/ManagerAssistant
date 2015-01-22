package com.extractor.dao;

/**
 * Created by Padonag on 19.11.2014.
 */
public class UserProxy {
    String proxyHost;
    Integer proxyPort;

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }
}
