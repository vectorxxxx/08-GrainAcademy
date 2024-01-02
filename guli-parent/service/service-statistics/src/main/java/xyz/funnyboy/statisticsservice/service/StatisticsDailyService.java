package xyz.funnyboy.statisticsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.statisticsservice.entity.StatisticsDaily;

import java.util.Map;

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

    /**
     * 显示图表
     *
     * @param type  类型
     * @param begin 开始日期
     * @param end   结束日期
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> showChart(String type, String begin, String end);
}
