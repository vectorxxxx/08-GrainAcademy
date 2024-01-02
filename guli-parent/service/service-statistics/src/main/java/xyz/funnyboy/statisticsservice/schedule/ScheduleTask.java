package xyz.funnyboy.statisticsservice.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.funnyboy.statisticsservice.service.StatisticsDailyService;
import xyz.funnyboy.statisticsservice.utils.DateUtil;

import java.util.Date;

/**
 * 定时任务
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 20:54:29
 */
@Component
public class ScheduleTask
{
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 每隔5秒执行一次，0秒开始
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void test() {
        System.out.println("**************task1执行了..");
    }

    /**
     * 每天凌晨一点，对前一天的数据进行统计
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void createStatisticsByDay() {
        final Date yesterday = DateUtil.addDays(new Date(), -1);
        final String yesterdayStr = DateUtil.formatDate(yesterday);
        statisticsDailyService.createStatisticsByDay(yesterdayStr);
    }
}
