package org.example.view;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import org.example.model.OverviewDataModel;
import org.example.model.Rule;

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
    private JLabel remainDayValue;
    private JLabel remainDayLabel;
    private JLabel countingRuleLabel;
    private JLabel countingRuleValue;
    private JScrollPane scrollPane;

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
        overviewForm.getCountingDayValue().setText(overview.getDayCount() + "");
        overviewForm.getRemainDayValue().setText(overview.getRemainDay() + "");
        overviewForm.getCountingRuleValue().setText(JSON.toJSONString(rule));
    }
}
