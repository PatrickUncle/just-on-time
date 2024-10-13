package org.example.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.example.common.Config;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * 以下时间使用每天的0点到当前时间的秒为单位进行时间界定。 以此来计算工时
 */
@Data
public class Rule {
    // 最早开始统计时间 TODO 页面上用的是时间戳，后台用的是int
    private int start;

    // 不被包含进工时的时段
    private List<StartEndPair> excludedList = new LinkedList<>();

    // 统计工时最晚时间（有可能会到达第二天凌晨）。 也代表着第二天能打上卡的最早时间
    private int end;
    // TODO 后续应该支持double类型
    private int dayAvgTime;

    private static Rule instance;

    public static Rule getInstance() {
        if (instance == null) {
            Rule rule = new Rule();
            init(rule);
            instance = rule;
        }
        return instance;
    }

    private Rule() {

    }

    private static void init(Rule current) {
        try {
            File configFile = new File(Config.CONF_FILE_PATH);
            if (!configFile.exists()) {
                // 60 * 8 * 60 + 30 * 60 = 早上八点半
                current.start = 30600;

                // 60 * 24 * 60 + 6 * 60 * 60 = 第二天凌晨六点
                current.end = 108000;

                // 午休时间 12：00 ～14：00
                StartEndPair noonBreak = new StartEndPair(43200, 50400);
                current.excludedList.add(noonBreak);

                // 晚餐时间 18：00～18：30
                StartEndPair dinner = new StartEndPair(64800, 66600);
                current.excludedList.add(dinner);

                // 日均工时
                current.dayAvgTime = 8;
            } else {
                String configContent = new String(Files.readAllBytes(Paths.get(Config.CONF_FILE_PATH)), StandardCharsets.UTF_8);
                Rule rule = JSON.parseObject(configContent, Rule.class);
                current.start = rule.getStart();
                current.end = rule.getEnd();
                current.excludedList = rule.getExcludedList();
                current.dayAvgTime = rule.getDayAvgTime();
            }
            try (BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(Config.CONF_FILE_PATH)), StandardCharsets.UTF_8))) {
                bf.write(JSON.toJSONString(current));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveRuleToFile() {
        try (BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(Config.CONF_FILE_PATH)), StandardCharsets.UTF_8))) {
            bf.write(JSON.toJSONString(this));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
