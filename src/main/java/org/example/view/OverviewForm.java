package org.example.view;

import lombok.Getter;
import org.example.model.DayTimeModel;

import javax.swing.*;


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
    private JLabel countingDayValue;
    private JLabel remainDayLabel;
    private JLabel remainDayValue;
    private JLabel countingRuleLabel;
    private JLabel countingRuleValue;
    private JScrollPane scrollPane;

    public static OverviewForm getInstance() {
        if (overviewForm == null) {
            overviewForm = new OverviewForm();
        }
        return overviewForm;
    }

    public void updateInfo(DayTimeModel dayTimeModel){
        overviewForm.getWorkingHourTotalValue().setText(dayTimeModel.getDate());
        overviewForm.getWorkingHourOverValue().setText(dayTimeModel.getStartTime() + "");
        overviewForm.getWorkingHourDayAvgValue().setText(dayTimeModel.getEndTime() + "");
    }
}
