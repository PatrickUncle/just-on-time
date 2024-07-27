package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import org.example.common.Config;
import org.example.frame.MainFrame;

import java.io.File;

public class App {

    public static void main(String[] args) throws NativeHookException {
        File recordPath = new File(Config.RECORD_ROOT_PATH);
        if (!recordPath.exists()) {
            recordPath.mkdirs();
        }
        MainFrame mainFrame = MainFrame.getInstance();
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        JustOnTime justOnTime = new JustOnTime();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseMotionListener(justOnTime);
    }

    private void init() {

    }

    private void initConfig() {

    }

    private void initCurrentMonthData() {

    }
}