package xyz.funnyboy.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.aclservice.entity.AclPermission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
public interface AclPermissionService extends IService<AclPermission>
{

    /**
     * 查询全部菜单
     *
     * @return {@link List}<{@link AclPermission}>
     */
    List<AclPermission> queryAllMenu();

    /**
     * 根据角色获取菜单
     *
     * @param roleId 角色 ID
     * @return {@link List}<{@link AclPermission}>
     */
    List<AclPermission> selectAllMenu(String roleId);

    /**
     * 给角色分配权限
     *
     * @param roleId       角色 ID
     * @param permissionId 权限 ID
     */
    void saveRolePermissionRelationShip(String roleId, String[] permissionId);

    /**
     * 递归删除菜单
     *
     * @param id 编号
     */
    void removeChildById(String id);

    /**
     * 根据用户ID获取用户菜单
     *
     * @param id 编号
     * @return {@link List}<{@link String}>
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 根据用户ID获取用户菜单
     *
     * @param id 编号
     * @return {@link List}<{@link JSONObject}>
     */
    List<JSONObject> selectPermissionByUserId(String id);
}
