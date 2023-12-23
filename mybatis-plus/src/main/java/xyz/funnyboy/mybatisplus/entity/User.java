package xyz.funnyboy.mybatisplus.entity;

/**
 * @author VectorX
 * @version V1.0
 * @description 用户
 * @date 19/12/2023
 */

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User
{
    /**
     * <a href="https://baomidou.com/pages/e131bd/">主键策略</a>
     */
    @TableId(type = IdType.ID_WORKER)
    // @TableId(type = IdType.ID_WORKER_STR)
    private Long id;

    private String name;
    private Integer age;
    private String email;

    /**
     * <a href="https://baomidou.com/pages/4c6bcf/">自动填充功能</a>
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // @TableField(fill = FieldFill.UPDATE)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * <a href="https://baomidou.com/pages/6b03c5/">逻辑删除</a>
     */
    @Version
    @TableField(fill = FieldFill.UPDATE)
    private Integer version;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
