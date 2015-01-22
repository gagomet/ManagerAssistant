package com.extractor.ui.model;

import com.extractor.model.Company;
import com.extractor.ui.view.companyform.CompanyForm;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

;

/**
 * Created by Padonag on 15.11.2014.
 */
public class OutputListItem {
    private static final String EMPTY_STRING = "";
    public static final String UNDEFINED = "undefined";

    public static Comparator<OutputListItem> EmailsComparator = new Comparator<OutputListItem>() {
        @Override
        public int compare(OutputListItem outputListItem, OutputListItem outputListItem2) {
            if (outputListItem.company.getEmail().equals(outputListItem2.company.getEmail())) {
                return 0;
            } else if (outputListItem.company.getEmail().equals(UNDEFINED)) {
                return 1;
            } else {
                return  -1;
            }
        }
    };

    private Company company;
    private JButton companyButton;

    public OutputListItem(final Company company) {
        this.company = company;
        companyButton = new JButton();
        companyButton.addActionListener(new CompanyButtonListener());
    }

    public Company getCompany() {
        return company;
    }

    public JButton getCompanyButton() {
        return companyButton;
    }

    public String toString() {
        if (company == null) {
            return EMPTY_STRING;
        }
        return company.getName() + "      " + company.getEmail();
    }

    private class CompanyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (company == null) {
                System.out.println("NPE");
            } else {
                System.out.println("hit the list item");
                CompanyForm companyForm = new CompanyForm(company);
            }
        }
    }
}
