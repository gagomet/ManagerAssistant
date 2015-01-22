package com.extractor;


import com.extractor.dao.impl.UaCompanyDao;
import com.extractor.model.Company;
import com.extractor.ui.controller.UiController;
import com.extractor.ui.model.UiModel;
import com.extractor.ui.view.UiView;

import java.util.List;


/**
 * Created by Padonag on 06.11.2014.
 */
public class StartApp {

    public static void main(String[] args) {


/*
        String url = "http://prom.ua/cc153703-Elektroizmeritelnye-pribory.html";
        PromUaDao pdao = new PromUaDao();
        List<Company> res = pdao.getCompanies(url, null);
*/
        String url = "http://www.ua-company.com/biznes/lichnaya-ohrana";
        UaCompanyDao udao = new UaCompanyDao();
        List<Company> res = udao.getCompanies(url, null);


        UiModel model = new UiModel();
        UiView view = new UiView(model);
        UiController controller = new UiController(view, model);
    }
}
