package xyz.funnyboy.aclservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.funnyboy.aclservice.entity.AclPermission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
public interface AclPermissionMapper extends BaseMapper<AclPermission>
{

    /**
     * 查询所有权限
     *
     * @return {@link List}<{@link String}>
     */
    List<String> selectAllPermissionValue();

    /**
     * 通过用户ID查询权限
     *
     * @param id 编号
     * @return {@link List}<{@link String}>
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 通过用户ID查询权限
     *
     * @param id 编号
     * @return {@link List}<{@link AclPermission}>
     */
    List<AclPermission> selectPermissionByUserId(String id);
}
