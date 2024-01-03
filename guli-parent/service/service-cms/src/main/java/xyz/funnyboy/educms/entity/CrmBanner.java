package xyz.funnyboy.educms.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.funnyboy.commonutils.BaseEntity;

/**
 * <p>
 * 首页banner表
 * </p>
 *
 * @author VectorX
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CrmBanner对象",
          description = "首页banner表")
public class CrmBanner extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    private String title;

    private String imageUrl;

    private String linkUrl;

    private Integer sort;
}
