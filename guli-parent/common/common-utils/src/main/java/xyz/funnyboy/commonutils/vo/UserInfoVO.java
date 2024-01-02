package xyz.funnyboy.commonutils.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员VO
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 19:52:29
 */
@Data
@ApiModel(value = "会员VO",
          description = "会员对象")
public class UserInfoVO
{
    @ApiModelProperty(value = "会员ID")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别 1 女，2 男")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "用户头像")
    private String avatar;
}
