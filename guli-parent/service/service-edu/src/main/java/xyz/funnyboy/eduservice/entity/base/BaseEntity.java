package xyz.funnyboy.eduservice.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
}
