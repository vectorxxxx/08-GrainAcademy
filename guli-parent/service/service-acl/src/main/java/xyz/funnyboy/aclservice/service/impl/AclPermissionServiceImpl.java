package xyz.funnyboy.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.aclservice.entity.AclPermission;
import xyz.funnyboy.aclservice.entity.AclRolePermission;
import xyz.funnyboy.aclservice.entity.AclUser;
import xyz.funnyboy.aclservice.helper.MenuHelper;
import xyz.funnyboy.aclservice.helper.PermissionHelper;
import xyz.funnyboy.aclservice.mapper.AclPermissionMapper;
import xyz.funnyboy.aclservice.service.AclPermissionService;
import xyz.funnyboy.aclservice.service.AclRolePermissionService;
import xyz.funnyboy.aclservice.service.AclUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
@Service
public class AclPermissionServiceImpl extends ServiceImpl<AclPermissionMapper, AclPermission> implements AclPermissionService
{

    @Autowired
    private AclRolePermissionService rolePermissionService;

    @Autowired
    private AclUserService userService;

    /**
     * 查询全部菜单
     *
     * @return {@link List}<{@link AclPermission}>
     */
    @Override
    public List<AclPermission> queryAllMenu() {
        // 查询权限数据
        final LambdaQueryWrapper<AclPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AclPermission::getId);
        final List<AclPermission> permissionList = this.list(queryWrapper);

        // 构建权限结构
        return PermissionHelper.build(permissionList);
    }

    /**
     * 根据角色获取菜单
     *
     * @param roleId 角色 ID
     * @return {@link List}<{@link AclPermission}>
     */
    @Override
    public List<AclPermission> selectAllMenu(String roleId) {
        // 查询所有权限
        final List<AclPermission> allPermissionList = this.list(new QueryWrapper<AclPermission>().orderByAsc("CAST(id as SIGNED)"));

        // 根据角色查询角色权限关系
        final List<String> permissionIdList = rolePermissionService.list(new LambdaQueryWrapper<AclRolePermission>().eq(AclRolePermission::getRoleId, roleId))
                                                                   .stream()
                                                                   .map(AclRolePermission::getPermissionId)
                                                                   .collect(Collectors.toList());

        // 设置权限的选中状态
        allPermissionList.forEach(permission -> permission.setSelect(permissionIdList.contains(permission.getId())));

        // 构建权限结构
        return PermissionHelper.build(allPermissionList);
    }

    /**
     * 给角色分配权限
     *
     * @param roleId        角色 ID
     * @param permissionIds 权限 ID
     */
    @Override
    public void saveRolePermissionRelationShip(String roleId, String[] permissionIds) {
        // 删除原有关系
        rolePermissionService.remove(new LambdaQueryWrapper<AclRolePermission>().eq(AclRolePermission::getRoleId, roleId));

        // 批量插入新的关系
        List<AclRolePermission> rolePermissionList = Arrays.stream(permissionIds)
                                                           .filter(id -> !StringUtils.isEmpty(id))
                                                           .map(permissionId -> {
                                                               AclRolePermission rolePermission = new AclRolePermission();
                                                               rolePermission.setRoleId(roleId);
                                                               rolePermission.setPermissionId(permissionId);
                                                               return rolePermission;
                                                           })
                                                           .collect(Collectors.toList());

        rolePermissionService.saveBatch(rolePermissionList);
    }

    /**
     * 递归删除菜单
     *
     * @param id 编号
     */
    @Override
    public void removeChildById(String id) {
        final List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);
        idList.add(id);
        this.removeByIds(idList);
    }

    /**
     * 根据用户ID获取用户菜单
     *
     * @param id 编号
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList;
        if (this.isSysAdmin(id)) {
            // 系统管理员，拥有最高权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        }
        else {
            // 获取用户权限值列表
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    /**
     * 根据用户ID获取用户菜单
     *
     * @param id 编号
     * @return {@link List}<{@link JSONObject}>
     */
    @Override
    public List<JSONObject> selectPermissionByUserId(String id) {
        List<AclPermission> selectPermissionList;
        if (this.isSysAdmin(id)) {
            // 系统管理员，拥有所有权限
            selectPermissionList = baseMapper.selectList(null);
        }
        else {
            // 获取用户权限
            selectPermissionList = baseMapper.selectPermissionByUserId(id);
        }

        final List<AclPermission> permissionList = PermissionHelper.build(selectPermissionList);
        return MenuHelper.build(permissionList);
    }

    /**
     * 是否系统管理员
     *
     * @param userId 用户 ID
     * @return boolean
     */
    private boolean isSysAdmin(String userId) {
        final AclUser user = userService.getById(userId);
        return user != null && "admin".equals(user.getUsername());
    }

    /**
     * 递归获取子节点
     *
     * @param id     ID
     * @param idList 子节点ID列表
     */
    private void selectChildListById(String id, List<String> idList) {
        this.list(new LambdaQueryWrapper<AclPermission>().select(AclPermission::getId)
                                                         .eq(AclPermission::getPid, id))
            .forEach(item -> {
                idList.add(item.getId());
                selectChildListById(item.getId(), idList);
            });
    }
}
