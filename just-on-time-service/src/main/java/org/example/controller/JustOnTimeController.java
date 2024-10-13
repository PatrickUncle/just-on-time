package org.example.controller;

import org.example.common.MyResult;
import org.example.model.OverviewDataModel;
import org.example.model.Rule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JustOnTimeController {


    @GetMapping("base")
    public MyResult base() {
        return MyResult.
                ok().
                data(OverviewDataModel.getInstance());
    }

    @GetMapping("config")
    public MyResult baseConfig() {
        return MyResult.ok().data(Rule.getInstance());
    }

    @GetMapping("excludeTimeRange")
    public MyResult excludeQuery() {
        return MyResult.ok().data(Rule.getInstance().getExcludedList());
    }

    @GetMapping("date")
    public MyResult queryDate(String date) {
        return MyResult.ok().data(date);
    }
}
