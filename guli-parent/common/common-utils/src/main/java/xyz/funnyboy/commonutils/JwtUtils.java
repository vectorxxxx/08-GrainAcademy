package xyz.funnyboy.commonutils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Jwt工具类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 15:09:33
 */
public class JwtUtils
{
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String createToken(String id, String nickname) {
        return Jwts.builder()
                   .setHeaderParam("typ", "JWT")
                   .setHeaderParam("alg", "HS512")
                   .setSubject("guli-user")
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                   .claim("id", id)
                   .claim("nickname", nickname)
                   .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                   .compact();
    }

    /**
     * 检查令牌
     *
     * @param jwtToken JWT令牌
     * @return boolean
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }

        try {
            Jwts.parser()
                .setSigningKey(APP_SECRET)
                .parseClaimsJws(jwtToken);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查令牌
     *
     * @param request 请求
     * @return boolean
     */
    public static boolean checkToken(HttpServletRequest request) {
        final String jwtToken = request.getHeader("token");
        return checkToken(jwtToken);
    }

    /**
     * 通过 JWT 令牌获取成员 ID
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        final String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) {
            return null;
        }

        return (String) Jwts.parser()
                            .setSigningKey(APP_SECRET)
                            .parseClaimsJws(jwtToken)
                            .getBody()
                            .get("id");
    }
}
