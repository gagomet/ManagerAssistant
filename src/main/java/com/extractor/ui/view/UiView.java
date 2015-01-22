package com.extractor.ui.view;

import com.extractor.ui.model.UiModel;
import com.extractor.ui.view.mainform.MainForm;

import com.extractor.ui.view.optionsform.OptionsForm;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 12.11.2014.
 */
public class UiView extends JFrame {
    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 600;
    private static final String FRAME_NAME = "Manager Assistant";

    private ResourceBundle text = ResourceBundle.getBundle("menus", Locale.ENGLISH);
    private UiModel model;
    private MainForm mainForm;
    private JMenuBar menubar;



    public UiView(UiModel model){
        this.model = model;
        setTitle(FRAME_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        mainForm = new MainForm(this);
        initMenu();
        add(mainForm);
        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setVisible(true);

    }

    public MainForm getMainForm() {
        return mainForm;
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    public UiModel getModel() {
        return model;
    }

    private void initMenu(){
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu fileMenu = new JMenu(text.getString("file.menu"));
        JMenu settingsMenu = new JMenu(text.getString("settings.menu"));
        JMenu aboutMenu = new JMenu(text.getString("about.menu"));
        menubar.add(fileMenu);
        menubar.add(settingsMenu);
        menubar.add(aboutMenu);
        JMenuItem exit = new JMenuItem(text.getString("file.exit"));
        fileMenu.add(exit);
        fileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        JMenuItem settings = new JMenuItem(text.getString("settings.proxy"));
        settingsMenu.add(settings);
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new OptionsForm(model);
            }
        });
        JMenuItem about = new JMenuItem(text.getString("about.about"));
        JMenuItem help = new JMenuItem(text.getString("about.help"));
        aboutMenu.add(about);
        aboutMenu.add(help);
    }

    public JMenuBar getMenubar() {
        return menubar;
    }
}
