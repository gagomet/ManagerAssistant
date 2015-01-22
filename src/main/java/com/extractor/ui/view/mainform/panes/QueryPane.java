package com.extractor.ui.view.mainform.panes;

import com.extractor.type.Sites;
import com.extractor.ui.view.UiView;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 12.11.2014.
 */
public class QueryPane extends JPanel {
    private JLabel queryStringLabel;
    private JTextField queryField;
    private JButton xButton;
    private JCheckBox uaRegionCheckbox;
    private JButton goButton;
    private JButton deepSearch;
    private JComboBox siteDropDown = new JComboBox(Sites.values());
    private static final String EMPTY_STRING = "";
    private UiView view;
    private ResourceBundle text = ResourceBundle.getBundle("text", Locale.ENGLISH);

    public QueryPane(UiView view) {
        this.view = view;
        setLayout(new FlowLayout());
        queryStringLabel = new JLabel(text.getString("query.label"));
        queryField = new JTextField(60);
        queryField.setText(EMPTY_STRING);
        xButton = new JButton(text.getString("xButton"));
        xButton.addActionListener(new XButtonListener());
        goButton = new JButton(text.getString("go.button"));
        deepSearch = new JButton(text.getString("deep.search.button"));
        add(queryStringLabel);
        add(queryField);
        add(xButton);
        add(siteDropDown);
        add(goButton);
        add(deepSearch);
        deepSearch.setVisible(false);
    }

    public void setSearchGoListener(ActionListener listener) {
        goButton.addActionListener(listener);
    }

    public void setDeepSearchListener(ActionListener listener){
        deepSearch.addActionListener(listener);
    }

    public JButton getDeepSearch() {
        return deepSearch;
    }

    public JTextField getQueryField() {
        return queryField;
    }

    public JComboBox getSiteDropDown() {
        return siteDropDown;
    }

    public JButton getGoButton() {
        return goButton;
    }

    public void clearText() {
        queryField.setText(EMPTY_STRING);
    }

    private class XButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            clearText();
            view.getModel().getListModel().clear();
            view.getMainForm().getButtonsPane().loadButtonOnly();
        }
    }
}



