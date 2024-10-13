package org.example.view;

import lombok.Getter;
import org.example.common.Config;
import org.example.model.OverviewDataModel;
import org.example.model.Rule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;


@Getter
public class OverviewForm {

    private static OverviewForm overviewForm;
    private JPanel mainPanel;
    private JLabel workingHourTotalLabel;
    private JLabel workingHourTotalValue;
    private JLabel workingHourOverLabel;
    private JLabel workingHourOverValue;
    private JLabel workingHourDayAvgLabel;
    private JLabel workingHourDayAvgValue;
    private JLabel workingHourLackLabel;
    private JLabel workingHourLackValue;
    private JLabel countingDayLabel;
    private JLabel countDays;
    private JLabel remainDayValue;
    private JLabel remainDayLabel;
    private JLabel adviceBackHomeTimeLabel;
    private JLabel adviceBackHomeTimeValue;
    private JScrollPane scrollPane;
    private JTable dateTable;
    private JLabel countingEndTimeValue;

    public static OverviewForm getInstance() {
        if (overviewForm == null) {
            overviewForm = new OverviewForm();
        }
        return overviewForm;
    }

    public void updateInfo() {
    }

    private OverviewForm() {
        DefaultTableModel model = new DefaultTableModel(Config.dateColumns, 0);
        this.dateTable.setModel(model);
    }
}
