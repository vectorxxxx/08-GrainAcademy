package xyz.funnyboy.commonutils;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 22:06:47
 */
@Data
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 8893910844063487919L;

    @TableId(value = "id",
             type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;
}
