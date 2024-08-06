package org.example.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.example.model.DayTimeModel;
import org.example.model.OverviewDataModel;
import org.example.model.Rule;
import org.example.model.StartEndPair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CommonUtils {

    public static String formatDate(Date date, DateFormat format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format.getValue());
        return sdf.format(date);
    }


    private static List<Calendar> getAllDay() {
        List<Calendar> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        // 从每月的1号开始
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        while (calendar.get(Calendar.MONTH) == currentMonth) {
            Calendar item = Calendar.getInstance();
            item.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            result.add(item);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return result;
    }

    public static List<String> getAllDayList() {
        return getAllDay().stream()
                .map(item -> CommonUtils.formatDate(item.getTime(), DateFormat.YEAR_MONTH_DAY))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<String> getWorkingDay() {
        List<Calendar> allDay = getAllDay();
        return allDay.stream().
                filter((item) -> item.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && item.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                .map((item) -> CommonUtils.formatDate(item.getTime(), DateFormat.YEAR_MONTH_DAY))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<String> getRestingDay() {
        return getAllDay().stream().
                filter((item) -> item.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || item.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                .map((item) -> CommonUtils.formatDate(item.getTime(), DateFormat.YEAR_MONTH_DAY))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String getCurrentMonthDataFilePath() {
        String month = formatDate(new Date(), DateFormat.YEAR_MONTH);
        return Config.CURRENT_MONTH_DATA_PATH_PRE + month;
    }

    public static List<DayTimeModel> getCurrentMonthData() throws IOException {
        List<DayTimeModel> monthData = new LinkedList<>();
        final String path = CommonUtils.getCurrentMonthDataFilePath();
        File file = new File(path);
        if (file.exists()) {
            String originData = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            // TODO 此处拿到的数据可能会有问题，最好做一下检查。
            monthData = JSON.parseObject(originData, new TypeReference<List<DayTimeModel>>() {
            });
        } else {
            List<String> allDayList = CommonUtils.getAllDayList();
            for (String day : allDayList) {
                monthData.add(new DayTimeModel(0, 0, day));
            }
        }
        return monthData;
    }


    public static void calculateHour(List<DayTimeModel> oneMonthData) {
        OverviewDataModel overview = OverviewDataModel.getInstance();
        int secondTotal = 0;
        int remainData = getRemainDay();
        int dayCount = getDayCount();
        for (DayTimeModel item : oneMonthData) {
            secondTotal += getOneDaySecondTotal(item);
        }
        overview.setTotal(secondsToHour(secondTotal));
        overview.setDayCount(dayCount);
        overview.setRemainDay(remainData);


        overview.setAvg(dayCount == 0 ? 0 : overview.getTotal() / overview.getDayCount());
        overview.setOver(overview.getTotal() - 8 * overview.getDayCount());
    }


    // 只统计到上一天
    private static int getDayCount() {
        // TODO 实现当月计时天数
        List<String> workingDay = getWorkingDay();
        // 从小到大
        workingDay.sort(Comparator.naturalOrder());
        String today = CommonUtils.formatDate(new Date(), DateFormat.YEAR_MONTH_DAY);
        int count = 0;
        for (String item : workingDay) {
            if (today.equals(item)) {
                break;
            }
            count++;
        }
        return count;
    }

    private static int getRemainDay() {
        // TODO 实现当月剩余工作日天数
        List<String> workingDay = getWorkingDay();
        return workingDay.size() - getDayCount();
    }

    public static int getOneDaySecondTotal(DayTimeModel dayTime) {
        List<String> restingDay = CommonUtils.getRestingDay();
        Rule rule = Rule.getInstance();
        // 先判断是否是休息日，当前休息日先不计算
        if (restingDay.contains(dayTime.getDate())) {
            return 0;
        }
        // TODO 工时计算
        long todayStart = stringDateToTimestamp(dayTime.getDate(), DateFormat.YEAR_MONTH_DAY);
        if (dayTime.getStartTime() == 0 || dayTime.getEndTime() == 0) {
            return 0;
        }
        int start = (int) (dayTime.getStartTime() - todayStart);
        int end = (int) (dayTime.getEndTime() - todayStart);
        int left = Math.max(start, rule.getStart());
        int right = end;
        int total = right - left;
        for (StartEndPair exclude : rule.getExcludedList()) {
            // 对每个exclude进行削减
            int excludeTime = getExcludeTime(left, right, exclude);
            total -= excludeTime;
        }
        return total;
    }

    public static double secondsToHour(long seconds) {
        return ((double) seconds) / (60 * 60);
    }

    public static int getExcludeTime(int left, int right, StartEndPair exclude) {
        // exclude全都在left的左边
        if (exclude.getEnd() <= left) {
            return 0;
        }
        // exclude与left相交，但未到right
        if (exclude.getEnd() <= right && exclude.getStart() <= left) {
            return exclude.getEnd() - left;
        }
        // exclude在left与right中间或者exclude与left、right完全重合
        if (exclude.getEnd() <= right && exclude.getStart() >= left) {
            return exclude.getEnd() - exclude.getStart();
        }
        // exclude在right右边，大于right边界
        if (exclude.getStart() >= right) {
            return 0;
        }
        // exclude与right相交，但未到left
        if (exclude.getEnd() > right && exclude.getStart() < right) {
            return right - exclude.getStart();
        }
        // exclude包含了left和right的范围
        if (exclude.getStart() < left && exclude.getEnd() > right) {
            return 0;
        }
        return 0;
    }

    public static Date getTodayDate() {
        Instant instant = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static long stringDateToTimestamp(String date, DateFormat dateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.getValue());
            return simpleDateFormat.parse(date).getTime() / 1000;
        } catch (ParseException e) {
            System.out.println("parse error");
        }
        return 0;
    }

    /**
     * 获取今天凌晨0点的时间戳
     *
     * @return 今天凌晨0点的时间戳
     */
    public static long getTodayStartTimestamp() {
        return getTodayDate().getTime() / 1000;
    }

    public void saveRuleToFile() {
        try (BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(Config.CONF_FILE_PATH)), StandardCharsets.UTF_8))) {
            bf.write(com.alibaba.fastjson.JSON.toJSONString(Rule.getInstance()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
