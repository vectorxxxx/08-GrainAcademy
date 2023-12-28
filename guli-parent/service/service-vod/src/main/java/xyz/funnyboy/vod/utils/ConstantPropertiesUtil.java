package xyz.funnyboy.vod.utils;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * VOD配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 19:45:40
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean
{
    @Value("${aliyun.vod.file.accesskeyid}")
    private String accessKeyId;
    @Value("${aliyun.vod.file.accesskeysecret}")
    private String accessKeySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

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
    }
}
