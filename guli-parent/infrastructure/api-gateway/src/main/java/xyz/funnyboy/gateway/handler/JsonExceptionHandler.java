package xyz.funnyboy.gateway.handler;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 16:05:27
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler
{
    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取错误属性
     *
     * @param request           请求
     * @param includeStackTrace 包括堆栈跟踪
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        // 创建一个HashMap对象
        final HashMap<String, Object> map = new HashMap<>();
        // 将success设置为false
        map.put("success", false);
        // 将code设置为20005
        map.put("code", 20005);
        // 将message设置为网关失败
        map.put("message", "网关失败");
        // 将data设置为null
        map.put("data", null);
        // 返回map对象
        return map;
    }

    /**
     * 获取路由函数：指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes 错误属性
     * @return {@link RouterFunction}<{@link ServerResponse}>
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 获取 HTTP 状态：根据code获取对应的HttpStatus
     *
     * @param errorAttributes 错误属性
     * @return int
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return 200;
    }
}
