package org.example.frame;


import org.example.view.OverviewForm;

import javax.swing.*;

/**
 * Main Frame
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2021/11/08.
 */
public class MainFrame extends JFrame {

    private static MainFrame instance;

    public static MainFrame getInstance() {
        if (instance == null) {
            return new MainFrame();
        }
        return instance;
    }

    private MainFrame() {
        this.setTitle("hello a ");
        this.setName("aaa");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 600);

        this.setContentPane(OverviewForm.getInstance().getMainPanel());
        this.setVisible(true);
    }
}
