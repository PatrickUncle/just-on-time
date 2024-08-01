package org.example;

import com.alibaba.fastjson.JSON;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import org.example.common.Config;
import org.example.common.CommonUtils;
import org.example.common.DateFormat;
import org.example.model.DayTimeModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 */
public class JustOnTime implements NativeMouseMotionListener {

    private final HashMap<String, DayTimeModel> timeMap = new HashMap<>();

    private long lastUpdateTime = 0;

    private static final String outputPre = Config.RECORD_ROOT_PATH + File.separator + "mouse_record";

    public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
        // 首先判断上一次更新是否满一分钟了
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        if (currentTimeSeconds - lastUpdateTime < 60) {
            return;
        }
        lastUpdateTime = currentTimeSeconds;
        String today = getToday();
        if (timeMap.containsKey(today)) {
            // 代表已经存储过
            DayTimeModel dayTimeModel = timeMap.get(today);
            dayTimeModel.setEndTime(lastUpdateTime);
        } else {
            DayTimeModel dayTimeModel = new DayTimeModel(lastUpdateTime, lastUpdateTime, today);
            timeMap.put(today, dayTimeModel);
        }
        try {
            writeRecord(timeMap.get(today));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeRecord(DayTimeModel dayTimeModel) throws IOException {
        String outputPath = outputPre + dayTimeModel.getDate();
        final String message = dayTimeModel.toString() + "\n";
        try (FileWriter fileWriter = new FileWriter(outputPath, true)) {
            fileWriter.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 往当前这个月的今天更新时间（包括start、end）。 填充其它时间的（start、end）
        // 此数据应当使用map存储，以便于迅速获取
        List<DayTimeModel> currentMonthData = CommonUtils.getCurrentMonthData();
        // TODO 处理今天最新的数据。 此处每次都从文件中读入又写出，过于频繁，后续考虑优化
        for (DayTimeModel item : currentMonthData) {
            // 后续使用map

            if (item.getDate().equals(CommonUtils.formatDate(CommonUtils.getTodayDate(), DateFormat.YEAR_MONTH_DAY))) {
                // 代表是今天
                if (item.getStartTime() == 0) {
                    item.setStartTime(dayTimeModel.getStartTime());
                }
                item.setEndTime(dayTimeModel.getEndTime());
                break;
            }
        }
        try (FileWriter fw = new FileWriter(CommonUtils.getCurrentMonthDataFilePath(), false)) {
            fw.write(JSON.toJSONString(currentMonthData));
        }
    }

    private String getToday() {
        return CommonUtils.formatDate(CommonUtils.getTodayDate(), DateFormat.YEAR_MONTH_DAY);
    }
}
