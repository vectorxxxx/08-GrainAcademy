package xyz.funnyboy.eduservice.client;

import org.springframework.stereotype.Component;
import xyz.funnyboy.commonutils.R;

/**
 * UcenterClient熔断实现
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-31 16:53:09
 */
@Component
public class UcenterClientImpl implements UcenterClient
{
    @Override
    public R getInfo(String id) {
        return R.error()
                .message("getInfo time out");
    }
}
