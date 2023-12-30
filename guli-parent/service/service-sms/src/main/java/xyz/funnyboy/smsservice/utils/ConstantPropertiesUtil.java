package xyz.funnyboy.smsservice.utils;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * SMS配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 19:45:40
 */
@Component
// 解决配置文件中中文乱码的问题，注意不能放在默认的application.properties中，必须新建配置文件才行
@PropertySource(value = "classpath:application-sms.properties",
                encoding = "UTF-8")
public class ConstantPropertiesUtil implements InitializingBean
{
    @Value("${aliyun.sms.accesskeyid}")
    private String accessKeyId;
    @Value("${aliyun.sms.accesskeysecret}")
    private String accessKeySecret;
    @Value("${aliyun.sms.signname}")
    private String signName;
    @Value("${aliyun.sms.templatecode}")
    private String templateCode;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        SIGN_NAME = signName;
        TEMPLATE_CODE = templateCode;
    }
}
