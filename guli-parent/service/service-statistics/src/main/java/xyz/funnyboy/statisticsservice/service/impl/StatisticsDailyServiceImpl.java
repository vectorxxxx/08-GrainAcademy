package xyz.funnyboy.statisticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.statisticsservice.client.UcenterClient;
import xyz.funnyboy.statisticsservice.entity.StatisticsDaily;
import xyz.funnyboy.statisticsservice.mapper.StatisticsDailyMapper;
import xyz.funnyboy.statisticsservice.service.StatisticsDailyService;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-02
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService
{
    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 按天创建统计信息
     *
     * @param day 日
     */
    @Override
    public void createStatisticsByDay(String day) {
        // 删除统计过的数据
        this.remove(new LambdaQueryWrapper<StatisticsDaily>().eq(StatisticsDaily::getDateCalculated, day));

        // 重新统计数据
        final Integer registerNum = (Integer) ucenterClient.countRegister(day)
                                                           .getData()
                                                           .get("countRegister");
        final int loginNum = RandomUtils.nextInt(100, 200);
        final int videoViewNum = RandomUtils.nextInt(100, 200);
        final int courseNum = RandomUtils.nextInt(100, 200);

        // 入库
        final StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setCourseNum(courseNum);
        this.save(statisticsDaily);
    }
}
