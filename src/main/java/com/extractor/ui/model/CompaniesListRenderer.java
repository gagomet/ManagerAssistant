package com.extractor.ui.model;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import java.awt.Component;


/**
 * Created by Padonag on 10.11.2014.
 */
public class CompaniesListRenderer extends JButton implements ListCellRenderer<OutputListItem> {

    @Override
    public Component getListCellRendererComponent(JList<? extends OutputListItem> list, OutputListItem value, int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setText(value.toString());
        setHorizontalAlignment(SwingConstants.LEFT);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;

    }
}
