package xyz.funnyboy.statisticsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.statisticsservice.entity.StatisticsDaily;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-02
 */
public interface StatisticsDailyService extends IService<StatisticsDaily>
{

    /**
     * 按天创建统计信息
     *
     * @param day 日
     */
    void createStatisticsByDay(String day);
}
