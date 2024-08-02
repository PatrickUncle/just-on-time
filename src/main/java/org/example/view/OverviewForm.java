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
        Rule rule = Rule.getInstance();
        OverviewDataModel overview = OverviewDataModel.getInstance();
        overviewForm.getWorkingHourTotalValue().setText(overview.getTotal() + "");
        overviewForm.getWorkingHourOverValue().setText(overview.getOver() + "");
        overviewForm.getWorkingHourDayAvgValue().setText(overview.getAvg() + "");
        overviewForm.getWorkingHourLackValue().setText("所欠工时");
        overviewForm.getCountDays().setText(overview.getDayCount() + "");
        overviewForm.getRemainDayValue().setText(overview.getRemainDay() + "");
        overviewForm.getAdviceBackHomeTimeValue().setText("18:00");
    }

    private OverviewForm() {
        DefaultTableModel model = new DefaultTableModel(Config.dateColumns, 0);
        this.dateTable.setModel(model);
    }

    public void updateDateList() {
        DefaultTableModel model = (DefaultTableModel) this.dateTable.getModel();
        Vector<Vector<Object>> rowData = new Vector<>();
        Vector<Object> row = new Vector<>();
        row.add("2024-07-31");
        row.add("2024-07-31 10:00");
        row.add("2024-07-31 18:00");
        rowData.add(row);
        model.setDataVector(rowData, Config.dateColumns);
    }


}
