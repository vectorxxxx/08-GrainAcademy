package xyz.funnyboy.orderservice.utils;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OSS配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-25 21:17:38
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean
{
    @Value("${wxpay.app-id}")
    private String appId;
    @Value("${wxpay.mch-id}")
    private String mchId;
    @Value("${wxpay.private-key}")
    private String privateKey;
    @Value("${wxpay.wx-pay-url}")
    private String wxPayUrl;
    @Value("${wxpay.notify-url}")
    private String notifyUrl;

    public static String APPID;
    public static String MCH_ID;
    public static String PRIVATE_KEY;
    public static String WX_PAY_URL;
    public static String NOTIFY_URL;

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appId;
        MCH_ID = mchId;
        PRIVATE_KEY = privateKey;
        WX_PAY_URL = wxPayUrl;
        NOTIFY_URL = notifyUrl;
    }
}
