package org.example.common;

import org.example.model.StartEndPair;
import org.junit.jupiter.api.Test;

public class CommonUtilsTest {


    @Test
    public void test_get_one_day_second_total_is_ok() {
        StartEndPair exclude = new StartEndPair(0, 2);

        assert CommonUtils.getExcludeTime(3, 8, exclude) == 0;

        exclude.setEnd(3);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 0;

        exclude.setEnd(4);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 1;

        exclude.setEnd(8);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 5;

        exclude.setStart(3);
        exclude.setEnd(8);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 5;

        exclude.setStart(5);
        exclude.setEnd(7);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 2;

        exclude.setEnd(8);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 3;

        exclude.setEnd(9);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 3;

        exclude.setStart(8);
        exclude.setEnd(10);
        assert CommonUtils.getExcludeTime(3, 8, exclude) == 0;
    }
}
