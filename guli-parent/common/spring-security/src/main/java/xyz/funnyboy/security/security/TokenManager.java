package xyz.funnyboy.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/03
 */
@Component
public class TokenManager
{

    private static final long TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    private static final String TOKEN_SIGN_KEY = "123456";

    /**
     * 创建令牌
     *
     * @param username 用户名
     * @return {@link String}
     */
    public String createToken(String username) {
        // 生成token
        return Jwts.builder()
                   // 设置token的subject
                   .setSubject(username)
                   // 设置token的过期时间
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                   // 设置token的签名算法和签名密钥
                   .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY)
                   // 压缩token
                   .compressWith(CompressionCodecs.GZIP)
                   // 生成token
                   .compact();
    }

    /**
     * 从令牌中获取用户
     *
     * @param token 令 牌
     * @return {@link String}
     */
    public String getUserFromToken(String token) {
        // 解析token，获取subject
        return Jwts.parser()
                   .setSigningKey(TOKEN_SIGN_KEY)
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    /**
     * 删除令牌
     *
     * @param token 令 牌
     */
    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

}
