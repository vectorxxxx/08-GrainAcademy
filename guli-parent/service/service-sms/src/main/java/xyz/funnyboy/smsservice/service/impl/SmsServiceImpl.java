package xyz.funnyboy.smsservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.funnyboy.smsservice.service.SmsService;
import xyz.funnyboy.smsservice.utils.ConstantPropertiesUtil;

import java.util.HashMap;

/**
 * 短信服务Service实现类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 15:29:44
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService
{
    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    private Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 发送短信
     *
     * @param code  验证码
     * @param phone 电话
     * @return boolean
     */
    @Override
    public boolean send(String code, String phone) {
        try {
            final HashMap<String, String> param = new HashMap<>();
            param.put("code", code);

            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    // 短信签名名称
                    .setSignName(ConstantPropertiesUtil.SIGN_NAME)
                    // 短信模板Code
                    .setTemplateCode(ConstantPropertiesUtil.TEMPLATE_CODE)
                    // 接收短信的手机号码
                    .setPhoneNumbers(phone)
                    // 短信模板变量对应的实际值。支持传入多个参数
                    .setTemplateParam(JSON.toJSONString(param));

            Client client = createClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            final SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
            final SendSmsResponseBody responseBody = resp.getBody();

            if (resp.getStatusCode() != 200 || !"OK".equals(responseBody.getCode())) {
                log.error("发送短信失败，错误码：{}，错误信息：{}", responseBody.getCode(), responseBody.getMessage());
                return false;
            }

            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
