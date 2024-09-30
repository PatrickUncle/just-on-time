package org.example.common;

import java.io.File;
import java.util.Vector;

public class Config {
    public static final String RECORD_ROOT_PATH = "records";

    public static final String DATA_PATH = "data";

    public static final String CURRENT_MONTH_DATA_PATH_PRE = DATA_PATH + File.separator + "monthData-";

    public static final Vector<String> dateColumns = new Vector<>();
    public static final String CONF_DIR_PATH = "conf";

    public static final String CONF_FILE_PATH = CONF_DIR_PATH + File.separator + "conf.json";

    static {
        dateColumns.add("日历");
        dateColumns.add("开始时间");
        dateColumns.add("结束时间");
    }
}

