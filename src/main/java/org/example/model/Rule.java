package org.example.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 以下时间使用每天的0点到当前时间的秒为单位进行时间界定。 以此来计算工时
 */
@Data
public class Rule {
    // 最早开始统计时间
    private int start;

    // 不被包含进工时的时段
    private List<StartEndPair> excludedList = new LinkedList<>();

    // 统计工时最晚时间（有可能会到达第二天凌晨）。 也代表着第二天能打上卡的最早时间
    private int end;

    private static Rule instance;

    public static Rule getInstance() {
        if (instance == null) {
            return new Rule();
        }
        return instance;
    }

    private Rule() {
        // 可以从配置文件中读取。 当前先写死一种情况
        // 60 * 8 * 60 + 30 * 60 = 早上八点半
        this.start = 30600;

        // 60 * 24 * 60 + 6 * 60 * 60 = 第二天凌晨六点
        this.end = 108000;

        // 午休时间 12：00 ～14：00
        StartEndPair noonBreak = new StartEndPair(43200, 50400);
        this.excludedList.add(noonBreak);

        // 晚餐时间 18：00～18：30
        StartEndPair dinner = new StartEndPair(64800, 66600);
        this.excludedList.add(dinner);
    }
}
