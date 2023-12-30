package xyz.funnyboy.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录VO
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 17:02:09
 */
@Data
@ApiModel(value = "注册VO",
          description = "注册对象")
public class RegisterVO
{
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
