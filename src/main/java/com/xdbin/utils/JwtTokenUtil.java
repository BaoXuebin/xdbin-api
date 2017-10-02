package com.xdbin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Author: baoxuebin
 * Date: 2017/7/1
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USER = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    // 密钥
    @Value("${jwt.secret}")
    private String secret;

    // token 过期时间
    @Value("${jwt.expiration}")
    private Long expiration;

    // 根据 用户信息 生成 token 字符串
    public String buildToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER, userId);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return buildToken(claims);
    }

    // 根据 token 获取用户id
    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    // 根据 token 获取创建时间
    public Date getCreateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            return new Date((Long) claims.get(CLAIM_KEY_CREATED));
        }
        return null;
    }

    // 验证 token 字符串的有效性
    public boolean validateToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (StringUtils.isEmpty(claims)) return false;

        String userId = claims.getSubject();
        Date expiration = claims.getExpiration();
        return !StringUtils.isEmpty(userId) && // 用户不为空
                new Date().before(expiration); // token 还没失效
    }

    // --------------- 私有方法 --------------

    // 根据 参数集合 生成 token 字符串
    private String buildToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(getExpiration())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    // 生成 token 过期时间
    private Date getExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 60 * 1000);
    }

    // 从 token 字符串获取 claims 因子
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

}
