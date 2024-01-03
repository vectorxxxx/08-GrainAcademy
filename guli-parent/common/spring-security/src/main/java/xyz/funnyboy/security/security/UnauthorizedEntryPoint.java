package xyz.funnyboy.security.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 未授权的统一处理方式
 * </p>
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/03
 * @see AuthenticationEntryPoint
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint
{

    /**
     * 开始
     *
     * @param request       请求
     * @param response      响应
     * @param authException 身份验证异常
     * @throws IOException      ioexception
     * @throws ServletException Servlet 异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ResponseUtil.out(response, R.error());
    }
}
