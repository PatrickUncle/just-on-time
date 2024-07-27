package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import org.example.model.DayTimeModel;

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

    private static final String outputPath = "mouse_record";

    public static void main(String[] args) throws NativeHookException {
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        JustOnTime justOnTime = new JustOnTime();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseMotionListener(justOnTime);
    }

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
    }

    private void writeRecord(DayTimeModel dayTimeModel) {
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
