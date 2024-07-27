package org.example;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import org.example.common.Config;
import org.example.model.DayTimeModel;
import org.example.view.OverviewForm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Hello world!
 */
public class JustOnTime implements NativeMouseMotionListener {

    private final HashMap<String, DayTimeModel> timeMap = new HashMap<>();

    private long lastUpdateTime = 0;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String outputPre = Config.RECORD_ROOT_PATH + File.separator + "mouse_record";

    public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
        // 首先判断上一次更新是否满一分钟了
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastUpdateTime < 60000) {
            return;
        }
        lastUpdateTime = currentTimeMillis;
        String today = getToday();
        if (timeMap.containsKey(today)) {
            // 代表已经存储过
            DayTimeModel dayTimeModel = timeMap.get(today);
            dayTimeModel.setEndTime(currentTimeMillis);
        } else {
            DayTimeModel dayTimeModel = new DayTimeModel(currentTimeMillis, 0, today);
            timeMap.put(today, dayTimeModel);
        }
        writeRecord(timeMap.get(today));
        OverviewForm.getInstance().updateInfo(timeMap.get(today));
    }

    private void writeRecord(DayTimeModel dayTimeModel) {
        String outputPath = outputPre + dayTimeModel.getDate();
        final String message = dayTimeModel.toString() + "\n";
        try (FileWriter fileWriter = new FileWriter(outputPath, true)) {
            fileWriter.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getToday() {
        return sdf.format(new Date());
    }
}
