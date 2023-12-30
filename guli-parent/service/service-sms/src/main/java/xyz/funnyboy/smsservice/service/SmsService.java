package xyz.funnyboy.smsservice.service;

/**
 * 短信服务Service
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 15:29:26
 */
public interface SmsService
{
    /**
     * 发送短信
     *
     * @param code  验证码
     * @param phone 电话
     * @return boolean
     */
    boolean send(String code, String phone);
}
