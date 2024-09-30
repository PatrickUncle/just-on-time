package org.example.common;

import lombok.Getter;

@Getter
public enum DateFormat {

    YEAR_MONTH_DAY("yyyy-MM-dd"),
    YEAR_MONTH_DAY_TIME("yyyy-MM-dd HH:mm:ss"),
    YEAR_MONTH("yyyy-MM"),

    HOUR_MINUTE_SECOND("HH:mm:ss");

    private String value;

    private DateFormat(String value) {
        this.value = value;
    }
}
