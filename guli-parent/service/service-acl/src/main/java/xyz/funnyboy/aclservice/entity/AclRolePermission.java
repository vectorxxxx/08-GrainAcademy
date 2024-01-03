package xyz.funnyboy.aclservice.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.funnyboy.commonutils.BaseEntity;

/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AclRolePermission对象",
          description = "角色权限")
public class AclRolePermission extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String permissionId;
}
