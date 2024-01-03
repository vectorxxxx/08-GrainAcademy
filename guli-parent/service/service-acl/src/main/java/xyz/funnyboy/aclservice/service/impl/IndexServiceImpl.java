package xyz.funnyboy.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.aclservice.entity.AclRole;
import xyz.funnyboy.aclservice.entity.AclUser;
import xyz.funnyboy.aclservice.service.AclPermissionService;
import xyz.funnyboy.aclservice.service.AclRoleService;
import xyz.funnyboy.aclservice.service.AclUserService;
import xyz.funnyboy.aclservice.service.IndexService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * IndexServiceImpl
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 17:50:36
 */

@Service
public class IndexServiceImpl implements IndexService
{
    @Autowired
    private AclUserService userService;

    @Autowired
    private AclRoleService roleService;

    @Autowired
    private AclPermissionService permissionService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username 用户名
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        // 根据用户名查询用户信息
        final AclUser user = userService.selectByUsername(username);
        final String userId = user.getId();

        // 根据用户ID查询用户角色名称
        final List<String> roleNameList = roleService.selectRoleByUserId(userId)
                                                     .stream()
                                                     .map(AclRole::getRoleName)
                                                     .collect(Collectors.toList());
        // 前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
        if (CollectionUtils.isEmpty(roleNameList)) {
            roleNameList.add("");
        }

        // 根据用户ID查询用户操作权限值
        final List<String> permissionValueList = permissionService.selectPermissionValueByUserId(userId);

        // 存入缓存
        redisTemplate.opsForValue()
                     .set(username, permissionValueList);

        // 返回用户信息
        final Map<String, Object> result = new HashMap<>();
        result.put("name", user.getUsername());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     *
     * @param username 用户名
     * @return {@link List}<{@link JSONObject}>
     */
    @Override
    public List<JSONObject> getMenu(String username) {
        // 根据用户名查询用户信息
        final AclUser user = userService.selectByUsername(username);

        // 根据用户ID查询用户菜单权限
        return permissionService.selectPermissionByUserId(user.getId());
    }
}
