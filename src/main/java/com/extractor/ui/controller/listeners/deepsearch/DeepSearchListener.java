package com.extractor.ui.controller.listeners.deepsearch;

import com.extractor.dao.ICompaniesDAO;
import com.extractor.model.Company;
import com.extractor.ui.controller.UiController;
import com.extractor.ui.controller.listeners.SubmitButtonListener;
import com.extractor.ui.model.OutputListItem;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Padonag on 20.11.2014.
 */
public class DeepSearchListener extends SubmitButtonListener implements Runnable {
    private ICompaniesDAO dao;
    Thread thread;

    public DeepSearchListener(UiController controller, ICompaniesDAO dao) {
        super(controller);
        this.dao = dao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        start();
    }


    public void start() {
        if (thread == null) {
            controller.getView().getMainForm().getResultsPane().getProgressBar().setVisible(true);
            thread = new Thread(this);
            thread.start();
        }
    }

    public void run() {
        List<Company> companies = new ArrayList<Company>();
        List<Company> tempList = null;
        int page = 2;
        StringBuilder builder = new StringBuilder();
        builder.append(controller.getView().getMainForm().getQueryPane().getQueryField().getText());
        builder.append(dao.getPageSuffix());
        builder.append(page);
        int totalPages = (dao.getNumberOfCompanies() / dao.getNumberCompaniesOnPage()) + 1;
        try {
            do {
                final int currentProgress = (page * 100) / totalPages;
                tempList = getCompaniesCheckedProxy(builder.toString(), dao);
                companies.addAll(tempList);
                int numberPos = builder.lastIndexOf("=") + 1;
                builder.delete(numberPos, builder.toString().length());
                controller.getView().getMainForm().getResultsPane().getProgressBar().setValue(currentProgress);
                page++;
                builder.append(page);
            } while (page <= dao.getNumberOfpages());
        } finally {
            controller.getModel().getListModel().addAll(convertToOutputList(companies));
            Collections.sort(controller.getModel().getListModel().getList(), OutputListItem.EmailsComparator);
            controller.getView().getMainForm().getResultsPane().getScrollPane().repaint();
            controller.getView().getMainForm().getResultsPane().getProgressBar().setVisible(false);
            controller.getView().getMainForm().getQueryPane().getDeepSearch().setVisible(false);
        }
    }
}
