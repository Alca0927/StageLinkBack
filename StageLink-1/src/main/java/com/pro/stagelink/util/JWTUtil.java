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

    // ✅ 실제 환경에서는 이 key를 외부 설정으로 관리해야 합니다.
    private static final String key = "12345678901234567890123456789012";


    public static final int ACCESS_TOKEN_EXP_MIN = 60;      // 1시간
    public static final int REFRESH_TOKEN_EXP_MIN = 10080;  // 7일


    // ✅ 공통 토큰 생성 함수
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

    // ✅ AccessToken 생성
    public static String generateAccessToken(Map<String, Object> claims) {
        return generateToken(claims, ACCESS_TOKEN_EXP_MIN, "access");
    }

    // ✅ RefreshToken 생성
    public static String generateRefreshToken(Map<String, Object> claims) {
        return generateToken(claims, REFRESH_TOKEN_EXP_MIN, "refresh");
    }

    // ✅ 토큰 검증 및 Claims 반환 (예외 상세 구분 포함)
    public static Map<String, Object> validateToken(String token) {
        try {
            SecretKey keyObj = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(keyObj)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new HashMap<>(claims);

        } catch (ExpiredJwtException e) {
            System.err.println("🔒 Access token expired: " + e.getMessage());
            throw new CustomJWTException("Token expired");
        } catch (UnsupportedJwtException e) {
            System.err.println("❌ Unsupported JWT: " + e.getMessage());
            throw new CustomJWTException("Unsupported token");
        } catch (MalformedJwtException e) {
            System.err.println("⚠️ Malformed JWT: " + e.getMessage());
            throw new CustomJWTException("Malformed token");
        } catch (SignatureException e) {
            System.err.println("🚨 Invalid JWT signature: " + e.getMessage());
            throw new CustomJWTException("Invalid signature");
        } catch (IllegalArgumentException e) {
            System.err.println("⚠️ Illegal token argument: " + e.getMessage());
            throw new CustomJWTException("Illegal token");
        } catch (Exception e) {
            System.err.println("❗ Unknown JWT error: " + e.getMessage());
            throw new CustomJWTException("Invalid token");
        }
    }
}
