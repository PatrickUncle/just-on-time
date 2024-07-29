package org.example.common;

import org.example.model.DayTimeModel;
import org.example.view.OverviewForm;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

    public static void registerTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    List<DayTimeModel> currentMonthData = CommonUtils.getCurrentMonthData();
                    CommonUtils.calculateHour(currentMonthData);
                    OverviewForm instance = OverviewForm.getInstance();
                    instance.updateInfo();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }
}
