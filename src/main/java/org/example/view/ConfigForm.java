package org.example.view;

import lombok.Getter;
import org.example.common.CommonUtils;
import org.example.common.Config;
import org.example.common.DateFormat;
import org.example.model.Rule;
import org.example.model.StartEndPair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.List;
import java.util.Vector;

@Getter
public class ConfigForm {
    private JPanel mainPanel;
    private JTable hourExcludeTable;
    private JButton addRule;
    private JButton deleteRule;
    private JLabel earliestStart;
    private JLabel latestEnd;
    private JLabel reachDayAvgTime;
    private JButton basicParamSetButton;

    public static final Vector<String> excludeHourColumns = new Vector<>();

    static {
        excludeHourColumns.add("序号");
        excludeHourColumns.add("开始时间");
        excludeHourColumns.add("结束时间");
    }

    private static ConfigForm instance;

    public static ConfigForm getInstance() {
        if (instance == null) {
            instance = new ConfigForm();
        }
        return instance;
    }

    private ConfigForm() {
        DefaultTableModel model = new DefaultTableModel(excludeHourColumns, 0);
        this.hourExcludeTable.setModel(model);
        this.updateConfig();
    }

    public void updateConfig() {
        Rule rule = Rule.getInstance();
        long todayStartTimestamp = CommonUtils.getTodayStartTimestamp();
        this.earliestStart.setText(timestampToStringClockDate((todayStartTimestamp + rule.getStart()) * 1000));
        this.latestEnd.setText(timestampToStringClockDate((todayStartTimestamp + rule.getEnd()) * 1000));
        this.reachDayAvgTime.setText(rule.getDayAvgTime() + "");

        DefaultTableModel model = (DefaultTableModel) this.hourExcludeTable.getModel();
        Vector<Vector<Object>> rowData = new Vector<>();
        List<StartEndPair> excludedList = rule.getExcludedList();
        for (int index = 0; index < excludedList.size(); index++) {
            StartEndPair startEndPair = excludedList.get(index);
            Vector<Object> row = new Vector<>();
            row.add(index + 1);
            row.add(timestampToStringClockDate((todayStartTimestamp + startEndPair.getStart()) * 1000));
            row.add(timestampToStringClockDate((todayStartTimestamp + startEndPair.getEnd()) * 1000));
            rowData.add(row);
        }
        model.setDataVector(rowData, excludeHourColumns);
    }

    private String timestampToStringClockDate(long timestamp) {
        return CommonUtils.formatDate(new Date(timestamp), DateFormat.YEAR_MONTH_DAY_TIME);
    }
}
