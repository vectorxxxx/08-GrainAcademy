package xyz.funnyboy.statisticsservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.statisticsservice.service.StatisticsDailyService;

/**
 * <p>
 * 统计分析管理
 * </p>
 *
 * @author VectorX
 * @since 2024-01-02
 */
@Api(description = "统计分析管理")
@CrossOrigin
@RestController
@RequestMapping("/statisticsservice/statistics-daily")
public class StatisticsDailyController
{
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation(value = "统计某一天的注册人数")
    @PostMapping("{day}")
    public R createStatisticsByDay(
            @ApiParam(name = "day",
                      value = "日期",
                      required = true)
            @PathVariable("day")
                    String day) {
        statisticsDailyService.createStatisticsByDay(day);
        return R.ok();
    }
}

