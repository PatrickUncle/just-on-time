package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class DayTimeModel {
    // 每天最早时间
    private long startTime;

    // 每天最晚时间
    private long endTime;

    // 当前日期
    private String date;
}
