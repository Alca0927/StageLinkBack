package com.pro.stagelink.util;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import javax.crypto.SecretKey;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
    private static final String key = "12345678901234567890123456789012";

    public static final int ACCESS_TOKEN_EXP_MIN = 60;
    public static final int REFRESH_TOKEN_EXP_MIN = 10080;

    // 공통 토큰 생성 (tokenType: "access", "refresh")
    public static String generateToken(Map<String, Object> valueMap, int min, String tokenType) {
        SecretKey keyObj = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        Map<String, Object> claims = new HashMap<>(valueMap);
        claims.put("tokenType", tokenType);

        return Jwts.builder()
            .setHeader(Map.of("typ", "JWT"))
            .setClaims(claims)
            .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
            .signWith(keyObj)
            .compact();
    }

    // AccessToken 생성
    public static String generateAccessToken(Map<String, Object> claims) {
        return generateToken(claims, ACCESS_TOKEN_EXP_MIN, "access");
    }

    // RefreshToken 생성
    public static String generateRefreshToken(Map<String, Object> claims) {
        return generateToken(claims, REFRESH_TOKEN_EXP_MIN, "refresh");
    }

    // 토큰 검증 및 claims 반환
    public static Map<String, Object> validateToken(String token) {
        try {
            SecretKey keyObj = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                .setSigningKey(keyObj)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException e) {
            throw new CustomJWTException(e instanceof ExpiredJwtException ? "Expired" : "Invalid");
        }
    }
}
