package xyz.funnyboy.gateway.filter;

import com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 全局Filter，统一处理会员登录与外部不允许访问的服务
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 15:48:43
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered
{
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final String path = request.getURI()
                .getPath();
        final ServerHttpResponse response = exchange.getResponse();
        // 校验用户是否登录
        if (antPathMatcher.match("/api/**/auth", path)) {
            final List<String> tokenList = request.getHeaders()
                    .get("token");
            if (CollectionUtils.isEmpty(tokenList)) {
                return out(response);
            }
            // final boolean isCheck = JwtUtils.checkToken(tokenList.get(0));
            // if (!isCheck) {
            return out(response);
            // }
        }
        // 内部服务接口，不允许外部访问
        if (antPathMatcher.match("/**/inner/**", path)) {
            return out(response);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> out(ServerHttpResponse response) {
        final JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 20004);
        message.addProperty("data", "鉴权失败");

        final byte[] bits = message.toString()
                .getBytes(StandardCharsets.UTF_8);
        final DataBuffer buffer = response.bufferFactory()
                .wrap(bits);

        // response.setStatusCode(HttpStatus.UNAUTHORIZED);

        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders()
                .add("Content-Type", "application/json;charset=UTF-8");

        // 创建一个非阻塞的单例，异步操作的预期结果是 buffer 的完成，这个单例将在 buffer 完成时返回。
        final Mono<DataBuffer> mono = Mono.just(buffer);

        // 将buffer写入到response中，并返回response
        return response.writeWith(mono);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
