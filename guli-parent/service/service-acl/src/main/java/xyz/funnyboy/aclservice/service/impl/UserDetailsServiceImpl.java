package xyz.funnyboy.aclservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.funnyboy.aclservice.entity.AclUser;
import xyz.funnyboy.aclservice.service.AclPermissionService;
import xyz.funnyboy.aclservice.service.AclUserService;
import xyz.funnyboy.security.entity.SecurityUser;
import xyz.funnyboy.security.entity.User;

import java.util.List;

/**
 * 自定义userDetailsService - 认证用户详情
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 22:53:17
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private AclUserService userService;

    @Autowired
    private AclPermissionService permissionService;

    /**
     * 按用户名加载用户
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 找不到用户名异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        final AclUser user = userService.selectByUsername(username);

        final User curUser = new User();
        BeanUtils.copyProperties(user, curUser);

        // 返回 userDetails 实现类
        final List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        final SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);

        return securityUser;
    }
}
