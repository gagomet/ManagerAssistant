package com.extractor.model;

import java.io.Serializable;

/**
 * Created by Padonag on 08.11.2014.
 */
public class Company implements Serializable {
    private static final long serialVersionUID = -7199399221357558978L;
    public static final String UNDEFINED = "undefined";

    private String name = UNDEFINED;
    private String address = UNDEFINED;
    private String email = UNDEFINED;
    private String phoneNumber = UNDEFINED;
    private String fax = UNDEFINED;
    private String site = UNDEFINED;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (!address.equals(company.address)) return false;
        if (!email.equals(company.email)) return false;
        if (!fax.equals(company.fax)) return false;
        if (!name.equals(company.name)) return false;
        if (!phoneNumber.equals(company.phoneNumber)) return false;
        if (!site.equals(company.site)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + fax.hashCode();
        result = 31 * result + site.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fax='" + fax + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
