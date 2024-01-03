package xyz.funnyboy.security.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.funnyboy.commonutils.MD5;

/**
 * 默认密码编码器
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/03
 * @see @see PasswordEncoder
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder
{

    public DefaultPasswordEncoder() {
        this(-1);
    }

    /**
     * @param strength the log rounds to use, between 4 and 31
     */
    public DefaultPasswordEncoder(int strength) {

    }

    /**
     * 加密
     *
     * @param rawPassword 原始密码
     * @return {@link String}
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    /**
     * 是否匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 编码密码
     * @return boolean
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
