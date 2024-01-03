package xyz.funnyboy.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xyz.funnyboy.aclservice.entity.AclRole;
import xyz.funnyboy.aclservice.entity.AclUserRole;
import xyz.funnyboy.aclservice.mapper.AclRoleMapper;
import xyz.funnyboy.aclservice.service.AclRoleService;
import xyz.funnyboy.aclservice.service.AclUserRoleService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
@Service
public class AclRoleServiceImpl extends ServiceImpl<AclRoleMapper, AclRole> implements AclRoleService
{

    @Autowired
    private AclUserRoleService userRoleService;

    /**
     * 根据用户获取角色数据
     *
     * @param userId 用户 ID
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        // 查询所有角色
        List<AclRole> allRolesList = baseMapper.selectList(null);

        // 根据用户id，查询用户拥有的角色id
        List<String> existRoleList = userRoleService.list(new LambdaQueryWrapper<AclUserRole>().select(AclUserRole::getRoleId)
                                                                                               .eq(AclUserRole::getUserId, userId))
                                                    .stream()
                                                    .map(AclUserRole::getRoleId)
                                                    .collect(Collectors.toList());
        // 对角色进行分类
        final List<AclRole> assignRoleList = allRolesList.stream()
                                                         .filter(role -> existRoleList.contains(role.getId()))
                                                         .collect(Collectors.toList());

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoleList", assignRoleList);
        roleMap.put("allRoleList", allRolesList);
        return roleMap;
    }

    /**
     * 根据用户分配角色
     *
     * @param userId  用户 ID
     * @param roleIds 角色 ID
     */
    @Override
    public void saveUserRoleRelationShip(String userId, String[] roleIds) {
        // 先删除原来的关系
        userRoleService.remove(new LambdaQueryWrapper<AclUserRole>().eq(AclUserRole::getUserId, userId));

        // 建立新的关系
        List<AclUserRole> userRoleList = Arrays.stream(roleIds)
                                               .filter(roleId -> !StringUtils.isEmpty(roleId))
                                               .map(roleId -> new AclUserRole().setRoleId(roleId)
                                                                               .setUserId(userId))
                                               .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<AclRole> selectRoleByUserId(String id) {
        // 根据用户id查询拥有的角色id
        List<String> roleIdList = userRoleService.list(new LambdaQueryWrapper<AclUserRole>().select(AclUserRole::getRoleId)
                                                                                            .eq(AclUserRole::getUserId, id))
                                                 .stream()
                                                 .map(AclUserRole::getRoleId)
                                                 .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Collections.emptyList();
        }

        // 根据角色id查询角色信息
        return baseMapper.selectBatchIds(roleIdList);
    }
}
