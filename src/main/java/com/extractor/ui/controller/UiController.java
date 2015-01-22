package com.extractor.ui.controller;

import com.extractor.ui.controller.listeners.ResultsPaneMouseListener;
import com.extractor.ui.controller.listeners.SubmitButtonListener;
import com.extractor.ui.model.UiModel;
import com.extractor.ui.view.UiView;

import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * Created by Padonag on 12.11.2014.
 */
public class UiController {
    private UiView view;
    private UiModel model;
    private SubmitButtonListener submitButtonListener;
    private MouseAdapter resultsPaneMouseListener;
    private ActionListener deepSearchListener;

    public UiController(UiView view, UiModel model) {
        this.view = view;
        this.model = model;
        submitButtonListener = new SubmitButtonListener(this);
        view.getMainForm().getQueryPane().setSearchGoListener(submitButtonListener);
        view.getMainForm().getQueryPane().setDeepSearchListener(deepSearchListener);
        view.getMainForm().getResultsPane().setListModel(model.getListModel());
        JList outputList = view.getMainForm().getResultsPane().getContentList();
        resultsPaneMouseListener = new ResultsPaneMouseListener(outputList);
        view.getMainForm().getResultsPane().setMouseListener(resultsPaneMouseListener);
    }

    public UiView getView() {
        return view;
    }

    public UiModel getModel() {
        return model;
    }

}
