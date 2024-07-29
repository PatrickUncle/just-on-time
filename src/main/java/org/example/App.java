package org.example;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import org.example.common.Config;
import org.example.common.CommonUtils;
import org.example.common.MyTimer;
import org.example.frame.MainFrame;
import org.example.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {

    private List<DayTimeModel> monthData;

    public static void main(String[] args) throws NativeHookException {
        // TODO 实现导入工作日历
        File recordPath = new File(Config.RECORD_ROOT_PATH);
        if (!recordPath.exists()) {
            recordPath.mkdirs();
        }

        MainFrame mainFrame = MainFrame.getInstance();

        MyTimer.registerTimer();
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        JustOnTime justOnTime = new JustOnTime();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseMotionListener(justOnTime);
    }

    private void init() {

    }

    private void initConfig() {

    }

    private void initCurrentMonthData() throws IOException {
        final String path = CommonUtils.getCurrentMonthDataFilePath();
        String originData = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        monthData = JSON.parseObject(originData, new TypeReference<List<DayTimeModel>>() {
        });
    }

}