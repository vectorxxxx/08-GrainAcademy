package xyz.funnyboy.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.aclservice.entity.AclRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
public interface AclRoleService extends IService<AclRole>
{

    /**
     * 根据用户获取角色数据
     *
     * @param userId 用户 ID
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * 根据用户分配角色
     *
     * @param userId 用户 ID
     * @param roleId 角色 ID
     */
    void saveUserRoleRelationShip(String userId, String[] roleId);

    List<AclRole> selectRoleByUserId(String id);
}
