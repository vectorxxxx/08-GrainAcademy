package xyz.funnyboy.security.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全认证用户详情信息
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 20:48:35
 */
@Data
@Slf4j
public class SecurityUser implements UserDetails
{

    private static final long serialVersionUID = 2323908873624544521L;

    /**
     * 当前用户信息
     */
    private transient User currentUserInfo;

    /**
     * 当前权限
     */
    private List<String> permissionValueList;

    public SecurityUser() {
    }

    public SecurityUser(User user) {
        if (user != null) {
            this.currentUserInfo = user;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissionValueList.stream()
                                  .filter(permissionValue -> !StringUtils.isEmpty(permissionValue))
                                  .map(SimpleGrantedAuthority::new)
                                  .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return currentUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUserInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

