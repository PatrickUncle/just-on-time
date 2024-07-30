package org.example.frame;


import org.example.components.AppTabbedPane;

import javax.swing.*;

/**
 * 主窗口
 */
public class MainFrame extends JFrame {

    private static MainFrame instance;

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    private MainFrame() {
        this.setTitle("按时下班");
        this.setName("按时下班");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 600);
        AppTabbedPane appTabbedPane = AppTabbedPane.getInstance();
        this.getRootPane().setJMenuBar(appTabbedPane);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
