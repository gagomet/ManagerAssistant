package com.extractor.ui.view.companyform;

import com.extractor.model.Company;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 12.11.2014.
 */
public class CompanyForm extends JFrame {
    private Company company;
    private ResourceBundle text = ResourceBundle.getBundle("companyform", Locale.ENGLISH);

    private JLabel nameLabel;
    private JTextField name = new JTextField(30);
    private JLabel addressLabel;
    private JTextField address = new JTextField(30);
    private JLabel emailLabel;
    private JTextField email = new JTextField(30);
    private JLabel phoneNumberLabel;
    private JTextField phoneNumber = new JTextField(30);
    private JLabel faxLabel;
    private JTextField fax = new JTextField(30);
    private JLabel siteLabel;
    private JTextField site = new JTextField(30);
    private JButton saveButton;
    private JButton okButton;

    public CompanyForm(Company company) {
        this.company = company;
        createForm();
        pack();
        setVisible(true);
    }

    private void createForm() {
        setTitle(text.getString("main") + " " + company.getName());
        setLayout(new GridLayout(0, 2));
        nameLabel = new JLabel(text.getString("name"));
        addressLabel = new JLabel((text.getString("address")));
        emailLabel = new JLabel(text.getString("email"));
        phoneNumberLabel = new JLabel(text.getString("phone.number"));
        faxLabel = new JLabel(text.getString("fax"));
        siteLabel = new JLabel(text.getString("site"));
        saveButton = new JButton(text.getString("save.button.text"));
        saveButton.addActionListener(new SaveButtonListener() );
        okButton = new JButton(text.getString("ok.button.text"));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        add(nameLabel);
        name.setText(company.getName());
        add(name);
        add(addressLabel);
        address.setText(company.getAddress());
        add(address);
        add(emailLabel);
        email.setText(company.getEmail());
        add(email);
        add(phoneNumberLabel);
        phoneNumber.setText(company.getPhoneNumber());
        add(phoneNumber);
        add(faxLabel);
        fax.setText(company.getFax());
        add(fax);
        add(siteLabel);
        site.setText(company.getSite());
        add(site);
        add(saveButton);
        add(okButton);
    }

    private class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveAndClose();
        }
    }

    private void close(){
        this.setVisible(false);
    }

    private void saveAndClose(){
        this.company.setName(name.getText());
        this.company.setAddress(address.getText());
        this.company.setEmail(email.getText());
        this.company.setPhoneNumber(phoneNumber.getText());
        this.company.setFax(fax.getText());
        this.company.setSite(site.getText());
        this.close();
    }

}
