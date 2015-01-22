package com.extractor.ui.view.mainform;

import com.extractor.ui.view.UiView;
import com.extractor.ui.view.mainform.panes.ButtonsPane;
import com.extractor.ui.view.mainform.panes.QueryPane;
import com.extractor.ui.view.mainform.panes.ResultsPane;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dimension;


/**
 * Created by Padonag on 12.11.2014.
 */
public class MainForm extends JPanel{
    private QueryPane queryPane;
    private ResultsPane resultsPane;
    private ButtonsPane buttonsPane;
    private UiView view;

    public MainForm(UiView view){
        this.view = view;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        queryPane = new QueryPane(view);
        resultsPane = new ResultsPane();
        buttonsPane = new ButtonsPane(resultsPane.getContentList(), this.view);
        buttonsPane.loadButtonOnly();
        add(queryPane);
        add(resultsPane);
        add(buttonsPane);
        queryPane.setVisible(true);
        resultsPane.setVisible(false);
        resultsPane.getScrollPane().setPreferredSize(new Dimension(UiView.FRAME_WIDTH - 50, UiView.FRAME_HEIGHT - 200));

    }

    public QueryPane getQueryPane() {
        return queryPane;
    }

    public ResultsPane getResultsPane() {
        return resultsPane;
    }

    public ButtonsPane getButtonsPane() {
        return buttonsPane;
    }

    public UiView getView() {
        return view;
    }
}
