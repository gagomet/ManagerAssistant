package com.extractor.ui.controller.listeners;

import com.extractor.dao.ICompaniesDAO;
import com.extractor.dao.impl.EmailOnlyDAO;
import com.extractor.dao.impl.RadaComUaDao;
import com.extractor.dao.impl.UaCompanyDao;
import com.extractor.dao.impl.UaRegionSiteDao;
import com.extractor.dao.impl.UiUaSiteDao;
import com.extractor.model.Company;
import com.extractor.type.Sites;
import com.extractor.ui.controller.UiController;
import com.extractor.ui.controller.listeners.deepsearch.DeepSearchListener;
import com.extractor.ui.model.OutputListItem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Padonag on 12.11.2014.
 */
public class SubmitButtonListener implements ActionListener, KeyListener {
    protected static final String EMPTY_STRING = "";
    private static final String URL_REGEX = "^(https?|ftp|file|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    protected UiController controller;
    protected ICompaniesDAO currentDao = null;
    private ResourceBundle messages = ResourceBundle.getBundle("parsers", Locale.ENGLISH);

    public SubmitButtonListener(UiController controller) {
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            action();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(controller.getView().getMainForm().getQueryPane().getGoButton())) {
            action();
        }
    }

    private void action() {
        controller.getModel().getListModel().clear();
        String query = controller.getView().getMainForm().getQueryPane().getQueryField().getText();
        if (EMPTY_STRING.equals(query)) {
            controller.getView().showError(messages.getString("empty.string"));
        } else if (!isUrl(query)) {
            controller.getView().showError(messages.getString("not.url"));
        } else {
            List<Company> companies;
            String address = controller.getView().getMainForm().getQueryPane().getQueryField().getText();

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            String check = controller.getView().getMainForm().getQueryPane().getSiteDropDown().getSelectedItem().toString();

            if (controller.getView().getMainForm().getQueryPane().getSiteDropDown().getSelectedItem() == Sites.DEFAULT) {
                currentDao = context.getBean(EmailOnlyDAO.class);
                } else if (controller.getView().getMainForm().getQueryPane().getSiteDropDown().getSelectedItem() == Sites.UA_REGION_INFO) {
                currentDao = context.getBean(UaRegionSiteDao.class);
                } else if (controller.getView().getMainForm().getQueryPane().getSiteDropDown().getSelectedItem() == Sites.UI_UA) {
                    currentDao = context.getBean(UiUaSiteDao.class);
                } else if (controller.getView().getMainForm().getQueryPane().getSiteDropDown().getSelectedItem() == Sites.RADA_COM_UA) {
                currentDao = context.getBean(RadaComUaDao.class);
                } /*else if(controller.getView().getMainForm().getQueryPane().getSiteDropDown().getSelectedItem() == Sites.PROM_UA){
                    currentDao = context.getBean(PromUaDao.class);
                }*/
                  else if(controller.getView().getMainForm().getQueryPane().getSiteDropDown().getSelectedItem() == Sites.UA_COMPANY){
                    currentDao = context.getBean(UaCompanyDao.class);
                }

            setListenerToDeepSearch(currentDao);
            companies = getCompaniesCheckedProxy(address, currentDao);
            controller.getModel().getListModel().setList(convertToOutputList(companies));
            Collections.sort(controller.getModel().getListModel().getList(), OutputListItem.EmailsComparator);
            controller.getView().getMainForm().getResultsPane().setVisible(true);
            controller.getView().getMainForm().getButtonsPane().allButtonsVisible();

        }
    }

    protected List<OutputListItem> convertToOutputList(List<Company> companyList) {
        List<OutputListItem> result = new ArrayList<OutputListItem>();
        for (Company tempCompany : companyList) {
            OutputListItem tempItem = new OutputListItem(tempCompany);
            result.add(tempItem);
        }
        return result;
    }

    private boolean isUrl(String dataString) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(dataString);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    protected List<Company> getCompaniesCheckedProxy(String address, ICompaniesDAO dao) {
        List<Company> companies;
        if (controller.getModel().getUserProxy() != null) {
            companies = dao.getCompanies(address, controller.getModel().getUserProxy());
        } else {
            companies = dao.getCompanies(address, null);
        }
        if(companies.equals(Collections.EMPTY_LIST)){
            controller.getView().getMainForm().getQueryPane().getDeepSearch().setVisible(false);
        }
        return companies;
    }

    private void setListenerToDeepSearch(ICompaniesDAO currentDao) {
        controller.getView().getMainForm().getQueryPane().setDeepSearchListener(new DeepSearchListener(controller, currentDao));
        controller.getView().getMainForm().getQueryPane().getDeepSearch().setVisible(true);
    }

}
