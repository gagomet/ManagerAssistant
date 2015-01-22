package com.extractor.ui.view.optionsform;

import com.extractor.dao.UserProxy;
import com.extractor.ui.model.UiModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Padonag on 18.11.2014.
 */
public class OptionsForm extends JFrame {
    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 70;
    private static final String IP_REGEX = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private static final String EMPTY_STRING = "";
    private ResourceBundle text = ResourceBundle.getBundle("options", Locale.ENGLISH);

    private JCheckBox usedProxy;
    private JLabel ip = new JLabel(text.getString("proxy.ip"));
    private JLabel port = new JLabel(text.getString("proxy.port"));
    private JTextField ipAddress = new JTextField(10);
    private JTextField portNumber = new JTextField(3);
    private JButton okButton = new JButton(text.getString("button.ok"));
    private JButton cancelButton = new JButton(text.getString("button.cancel"));
    private UiModel model;
    private boolean isOptionsVisible = false;
    private Pattern pattern = Pattern.compile(IP_REGEX);


    public OptionsForm(UiModel model) {
        this.model = model;
        createForm();
        setTitle(text.getString("frame.name"));
        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setVisible(true);
        setVisibilityOptions(false);
    }

    private void createForm() {
        setLayout(new FlowLayout());
        ipAddress.setText(EMPTY_STRING);
        portNumber.setText(EMPTY_STRING);
        usedProxy = new JCheckBox(text.getString("proxy.checkbox"), false);
        usedProxy.setMnemonic(KeyEvent.VK_SPACE);
        add(usedProxy);
        usedProxy.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (!isOptionsVisible) {
                    setVisibilityOptions(true);
                    isOptionsVisible = !isOptionsVisible;
                } else {
                    setVisibilityOptions(false);
                    isOptionsVisible = !isOptionsVisible;
                }
            }
        });
        add(ip);
        add(ipAddress);
        add(port);
        add(portNumber);
        add(okButton);
        okButton.addActionListener(new OkButtonListener());
        add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
            }
        });
    }

    private void setVisibilityOptions(boolean visibility) {
        this.ipAddress.setVisible(visibility);
        this.portNumber.setVisible(visibility);
        this.ip.setVisible(visibility);
        this.port.setVisible(visibility);
    }

    private boolean isIpAddress(final String string) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    private void close(){
        this.setVisible(false);
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    private class OkButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (isIpAddress(ipAddress.getText())) {
                if (isParsable(portNumber.getText())) {
                    int port = Integer.parseInt(portNumber.getText());
                    if (port >= 1 && port < 65536) {
                        UserProxy userProxy = new UserProxy();
                        userProxy.setProxyHost(ipAddress.getText());
                        userProxy.setProxyPort(Integer.parseInt(portNumber.getText()));
                        model.setUserProxy(userProxy);
                        close();
                    } else {
                        showError(text.getString("error.port.number"));
                    }
                } else {
                    showError(text.getString("error.port"));
                }
            } else {
                showError(text.getString("error.ip"));
            }
        }

        private boolean isParsable(String input) {
            boolean parsable = true;
            try {
                Integer.parseInt(input);
            } catch (NumberFormatException e) {
                parsable = false;
            }
            return parsable;
        }

    }
}
