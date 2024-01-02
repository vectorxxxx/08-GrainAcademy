package xyz.funnyboy.statisticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.statisticsservice.client.UcenterClient;
import xyz.funnyboy.statisticsservice.entity.StatisticsDaily;
import xyz.funnyboy.statisticsservice.mapper.StatisticsDailyMapper;
import xyz.funnyboy.statisticsservice.service.StatisticsDailyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 显示图表
     *
     * @param type  类型
     * @param begin 开始日期
     * @param end   结束日期
     */
    @Override
    public Map<String, Object> showChart(String type, String begin, String end) {
        // 条件查询
        final QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.select("date_calculated", type);
        final List<StatisticsDaily> statisticsDailyList = this.list(queryWrapper);

        // 日期数据
        List<String> dateCalculatedList = new ArrayList<>();
        // 数量数据
        List<Integer> numDataList = new ArrayList<>();

        // 处理数据
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {
            dateCalculatedList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numDataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        // 返回数据
        Map<String, Object> chart = new HashMap<>();
        chart.put("date_calculatedList", dateCalculatedList);
        chart.put("numDataList", numDataList);

        return chart;
    }
}
