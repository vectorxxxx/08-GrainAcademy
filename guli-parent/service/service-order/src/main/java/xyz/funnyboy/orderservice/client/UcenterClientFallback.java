package xyz.funnyboy.orderservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.commonutils.vo.UserInfoVO;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-01 23:40:26
 */
@Component
public class UcenterClientFallback implements UcenterClient
{
    @Override
    public UserInfoVO getInfoRemote(
            @PathVariable("id")
                    String id) {
        return null;
    }
}
