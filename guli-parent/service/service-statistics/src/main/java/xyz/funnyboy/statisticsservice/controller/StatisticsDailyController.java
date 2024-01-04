package xyz.funnyboy.statisticsservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.statisticsservice.service.StatisticsDailyService;

import java.util.Map;

/**
 * <p>
 * 统计分析管理
 * </p>
 *
 * @author VectorX
 * @since 2024-01-02
 */
@Api(description = "统计分析管理")
// @CrossOrigin
@RestController
@RequestMapping("/statisticsservice/statistics")
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

    @ApiOperation(value = "显示图表")
    @GetMapping("/showChart/{type}/{begin}/{end}")
    public R showChart(
            @ApiParam(name = "type",
                      value = "统计类型",
                      required = true)
            @PathVariable("type")
                    String type,

            @ApiParam(name = "begin",
                      value = "开始日期",
                      required = true)
            @PathVariable("begin")
                    String begin,

            @ApiParam(name = "end",
                      value = "结束日期",
                      required = true)
            @PathVariable("end")
                    String end) {
        final Map<String, Object> chart = statisticsDailyService.showChart(type, begin, end);
        return R.ok()
                .data(chart);
    }
}

