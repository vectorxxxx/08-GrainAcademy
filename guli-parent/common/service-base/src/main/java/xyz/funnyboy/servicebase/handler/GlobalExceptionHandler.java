package xyz.funnyboy.servicebase.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.servicebase.exception.GuliException;

/**
 * 统一异常处理类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-23 21:24:24
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        log.error(e.getMessage(), e);
        return R.error()
                .message(e.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        log.error(e.getMessage(), e);
        return R.error()
                .message("算数异常：" + e.getMessage());
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        log.error(e.getMsg());
        return R.error()
                .code(e.getCode())
                .message(e.getMsg());
    }
}
