package com.extractor.ui.model;

import com.extractor.model.Company;

import javax.swing.JList;
import java.awt.event.ActionListener;

/**
 * Created by Padonag on 13.11.2014.
 */
public class CompaniesList extends JList {
    private ActionListener actionListener;

    public CompaniesList(ArrayListModel<Company> entryData){
        super(entryData);
    }

   //TODO make Jlist clickable
}
