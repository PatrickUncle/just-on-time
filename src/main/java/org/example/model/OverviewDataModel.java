package org.example.model;

import lombok.Data;

@Data
public class OverviewDataModel {
    private double total;

    private double over;

    private double avg;

    private int dayCount;

    private int remainDay;

    private static OverviewDataModel instance;

    public static OverviewDataModel getInstance(){
        if (instance == null) {
            instance = new OverviewDataModel();
        }
        return instance;
    }
}
