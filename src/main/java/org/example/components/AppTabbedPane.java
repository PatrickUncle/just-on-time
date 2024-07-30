package org.example.components;

import org.example.view.OverviewForm;

import javax.swing.*;

/**
 * 页面tab切换控制
 */
public class AppTabbedPane extends JMenuBar {

    private static AppTabbedPane instance;

    private JTabbedPane jTabbedPane;

    public static AppTabbedPane getInstance() {
        if (instance == null) {
            instance = new AppTabbedPane();
        }
        return instance;
    }

    private AppTabbedPane() {
        jTabbedPane = new JTabbedPane();

        jTabbedPane.addTab("概览", OverviewForm.getInstance().getMainPanel());
//        jTabbedPane.addTab("考勤设置", panel2);
//        jTabbedPane.addTab("系统设置", panel3);

        this.add(jTabbedPane);
    }
}
