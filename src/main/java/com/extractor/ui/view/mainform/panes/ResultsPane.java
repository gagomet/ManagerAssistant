package com.extractor.ui.view.mainform.panes;

import com.extractor.ui.model.ArrayListModel;
import com.extractor.ui.model.CompaniesListRenderer;
import com.extractor.ui.model.OutputListItem;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;



/**
 * Created by Padonag on 12.11.2014.
 */
public class ResultsPane extends JPanel {

    private JList<OutputListItem> contentList;
    private JScrollPane scrollPane;
    private JProgressBar progressBar;

    public ResultsPane(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        contentList = new JList<OutputListItem>();
        contentList.setCellRenderer(new CompaniesListRenderer());
        contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(contentList);
        Border border = BorderFactory.createTitledBorder("Companies found");
        scrollPane.setBorder(border);
        add(scrollPane);
        progressBar = new JProgressBar();
        add(progressBar);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
    }

    public void setMouseListener(MouseAdapter adapter){
        this.contentList.addMouseListener(adapter);
    }

    public void setListModel(ArrayListModel model) {
        contentList.setModel(model);
    }

    public JList<OutputListItem> getContentList() {
        return contentList;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

}
