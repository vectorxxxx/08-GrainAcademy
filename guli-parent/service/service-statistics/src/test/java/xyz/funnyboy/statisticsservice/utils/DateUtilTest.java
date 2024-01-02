package xyz.funnyboy.statisticsservice.utils;

import org.junit.Test;

import java.util.Date;

/**
 * DateUtil测试类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 21:04:17
 */
public class DateUtilTest
{
    @Test
    public void test() {
        final Date now = new Date();
        System.out.println(DateUtil.formatDate(now));
        final Date yesterday = DateUtil.addDays(now, -1);
        System.out.println(DateUtil.formatDate(yesterday));
    }
}
