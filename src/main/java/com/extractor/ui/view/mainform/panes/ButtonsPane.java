package com.extractor.ui.view.mainform.panes;

import com.extractor.file.ImportToTxtAddressBook;
import com.extractor.file.impl.XlsFilesManager;
import com.extractor.model.Company;
import com.extractor.ui.model.OutputListItem;
import com.extractor.ui.view.UiView;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 15.11.2014.
 */
public class ButtonsPane extends JPanel {

    private ResourceBundle text = ResourceBundle.getBundle("text", Locale.ENGLISH);
    private JButton addNewBlankCompany;
    private JButton saveToXlsFileButton;
    private JButton loadFromXlsFileButton;
    private JButton saveToTxtFileButton;
    private JButton clearOutput;
    private JList content;
    private UiView view;

    public ButtonsPane(JList content, UiView view) {
        this.view = view;
        this.content = content;
        setLayout(new FlowLayout());
        addNewBlankCompany = new JButton(text.getString("new.company"));
        saveToXlsFileButton = new JButton(text.getString("save.xls"));
        loadFromXlsFileButton = new JButton(text.getString("load.xls"));
        saveToTxtFileButton = new JButton(text.getString("save.txt"));
        clearOutput = new JButton(text.getString("clear"));
        ButtonsListener buttonsListener = new ButtonsListener();
        add(addNewBlankCompany);
        addNewBlankCompany.addActionListener(buttonsListener);
        add(saveToXlsFileButton);
        saveToXlsFileButton.addActionListener(buttonsListener);
        add(loadFromXlsFileButton);
        loadFromXlsFileButton.addActionListener(buttonsListener);
        add(saveToTxtFileButton);
        saveToTxtFileButton.addActionListener(buttonsListener);
        add(clearOutput);
        clearOutput.addActionListener(buttonsListener);
    }

    private List<Company> getListToSaveToFile(JList contentList) {
        List<Company> result = new ArrayList<Company>();
        if (contentList == null) {
            return Collections.EMPTY_LIST;
        } else {
            for (int i = 0; i < contentList.getModel().getSize(); i++) {
                OutputListItem item = (OutputListItem) contentList.getModel().getElementAt(i);
                Company tmpCompany = item.getCompany();
                result.add(tmpCompany);
            }
        }
        return result;

    }

    public void allButtonsVisible() {
        saveToXlsFileButton.setVisible(true);
        loadFromXlsFileButton.setVisible(true);
        saveToTxtFileButton.setVisible(true);
        clearOutput.setVisible(true);
    }

    public void loadButtonOnly() {
        saveToXlsFileButton.setVisible(false);
        loadFromXlsFileButton.setVisible(true);
        saveToTxtFileButton.setVisible(false);
        clearOutput.setVisible(false);
    }


    /*LISTENER!!!!*/

    private class ButtonsListener implements ActionListener {
        private JFileChooser fileChooser = new JFileChooser();
        private FileFilter xlsFilter = new FileNameExtensionFilter("Microsoft excel files", "xls");
        private FileFilter txtFilter = new FileNameExtensionFilter("Text files", "txt");

        public ButtonsListener() {
            fileChooser.addChoosableFileFilter(xlsFilter);
            fileChooser.addChoosableFileFilter(txtFilter);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            XlsFilesManager filesManager = new XlsFilesManager();
                                             /*SAVE TO XLS BUTTON*/

            if (e.getSource() == saveToXlsFileButton){
                int returnVal = fileChooser.showSaveDialog(ButtonsPane.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.exists()) {
                        file = filesManager.createXlsFile(fileChooser.getSelectedFile().getAbsolutePath(), getListToSaveToFile(content));
                    } else {
                        filesManager.updateXlsFile(file, getListToSaveToFile(content));
                    }
                }
            }                           /*LOAD FROM XLS BUTTON*/
            else if (e.getSource() == loadFromXlsFileButton) {
                int returnVal = fileChooser.showOpenDialog(ButtonsPane.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    System.out.println("OLOLOADER!!!");
                    List<Company> companiesFromFile = new ArrayList<Company>();
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        companiesFromFile = filesManager.readCompaniesListFromXlsFile(fis);
                        fis.close();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if (view.getModel().getListModel().getSize() == 0) {
                        view.getMainForm().getResultsPane().setVisible(true);
                    }
                    view.getModel().getListModel().addAll(getOutputList(companiesFromFile));
                    view.getMainForm().getButtonsPane().allButtonsVisible();
                }
            }

                                            /*SAVE TO TXT FILE*/
            else if(e.getSource() == saveToTxtFileButton){
                ImportToTxtAddressBook txtWriter = new ImportToTxtAddressBook();
                int returnVal = fileChooser.showSaveDialog(ButtonsPane.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.exists()) {
                        file = txtWriter.createNewTxtAddressBook(fileChooser.getSelectedFile().getAbsolutePath(), getListToSaveToFile(content));
                    } else {
                        txtWriter.writeCompaniesToTxtFile(getListToSaveToFile(content), file);
                    }
                }
            }

                                            /*CLEAR LIST FILE*/
            else if(e.getSource() == clearOutput){
                view.getModel().getListModel().clear();
            }

                                            /*ADD NEW COMPANY*/
            else if(e.getSource() == addNewBlankCompany){
                Company blankCompany = new Company();
                blankCompany.setName(text.getString("blank"));
                OutputListItem newListItem = new OutputListItem(blankCompany);
                view.getModel().getListModel().addOneElement(newListItem);
                view.getMainForm().getResultsPane().setVisible(true);
                view.getMainForm().getButtonsPane().allButtonsVisible();
            }

        }

        private List<OutputListItem> getOutputList(List<Company> companyList) {
            if (companyList == null) {
                return Collections.EMPTY_LIST;
            } else {
                List<OutputListItem> result = new ArrayList<OutputListItem>();
                for (Company tmp : companyList) {
                    OutputListItem outTmp = new OutputListItem(tmp);
                    result.add(outTmp);
                }
                return result;
            }
        }
    }
}
