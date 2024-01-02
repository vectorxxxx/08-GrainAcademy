package xyz.funnyboy.statisticsservice.client;

import org.springframework.stereotype.Component;
import xyz.funnyboy.commonutils.R;

/**
 * 会员远程调用失败回调
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 20:28:09
 */
@Component
public class UcenterClientFallback implements UcenterClient
{
    @Override
    public R countRegister(String day) {
        return R.error()
                .message("统计某一天的注册人数远程调用失败");
    }
}
