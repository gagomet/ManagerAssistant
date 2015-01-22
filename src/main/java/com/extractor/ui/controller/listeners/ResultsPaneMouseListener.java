package com.extractor.ui.controller.listeners;

import com.extractor.ui.model.OutputListItem;

import javax.swing.JList;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Padonag on 14.11.2014.
 */
public class ResultsPaneMouseListener extends MouseAdapter {
    private JList<OutputListItem> contentList;

    public ResultsPaneMouseListener(JList<OutputListItem> contentList) {
        this.contentList = contentList;
    }

    public void mouseClicked(MouseEvent e){
        clickButtonAt(e.getPoint());
    }

    private void clickButtonAt(Point point) {
        int index = contentList.locationToIndex(point);
        OutputListItem item = contentList.getModel().getElementAt(index);
        item.getCompanyButton().doClick();
    }


}
