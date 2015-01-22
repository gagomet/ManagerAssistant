package com.extractor.ui.model;

import com.extractor.dao.UserProxy;

/**
 * Created by Padonag on 14.11.2014.
 */
public class UiModel {

    private ArrayListModel<OutputListItem> listModel = new ArrayListModel<OutputListItem>();

    /*private boolean useProxy = false;*/
    private UserProxy userProxy = null;

    public ArrayListModel getListModel() {
        return listModel;
    }

    public void clearListModel() {
        listModel = null;
    }

    public UserProxy getUserProxy() {
        return userProxy;
    }

    public void setUserProxy(UserProxy userProxy) {
        this.userProxy = userProxy;
    }
}
