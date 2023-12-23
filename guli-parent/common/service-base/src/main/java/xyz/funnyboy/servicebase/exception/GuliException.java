package xyz.funnyboy.servicebase.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-23 21:36:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException
{
    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String msg;

    @Override
    public String toString() {
        return "GuliException{" + "code=" + code + ", msg='" + msg + '\'' + ", message='" + this.getMessage() + '\'' + '}';
    }
}
