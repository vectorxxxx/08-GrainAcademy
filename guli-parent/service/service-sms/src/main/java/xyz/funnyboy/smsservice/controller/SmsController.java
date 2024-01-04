package xyz.funnyboy.smsservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.RandomUtil;
import xyz.funnyboy.smsservice.service.SmsService;

import java.util.concurrent.TimeUnit;

/**
 * 短信服务Controller
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 15:27:57
 */
@Api(description = "短信服务")
@RestController
@RequestMapping("/edusms/sms")
// @CrossOrigin
public class SmsController
{
    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "发送验证码")
    @GetMapping("send/{phone}")
    public R send(
            @PathVariable
                    String phone) {
        String code = redisTemplate.opsForValue()
                                   .get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //生成4位随机数
        code = RandomUtil.getFourBitRandom();
        //调用smsService的send方法，发送验证码
        boolean isSend = smsService.send(code, phone);
        //判断是否发送成功
        if (isSend) {
            //将code放入redis，设置过期时间为5分钟
            redisTemplate.opsForValue()
                         .set(phone, code, 5, TimeUnit.MINUTES);
            //返回发送成功的结果
            return R.ok()
                    .data("code", code);
        }
        else {
            //返回发送失败的结果
            return R.error()
                    .message("验证码发送失败");
        }
    }

}
