package com.pro.stagelink.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.util.CustomJWTException;
import com.pro.stagelink.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIFefreshController {

    @PostMapping("/admin/api/login/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new CustomJWTException("NULL_REFRESH");
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomJWTException("INVALID_TOKEN_FORMAT");
        }

        String accessToken = authHeader.substring(7);

        // accessToken이 아직 유효하면 기존 토큰 반환
        if (!isTokenExpired(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // refreshToken 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        if (!"refresh".equals(claims.get("tokenType"))) {
            throw new CustomJWTException("NOT_REFRESH_TOKEN");
        }

        log.info("refresh...claims: {}", claims);

        // 새 accessToken 생성
        String newAccessToken = JWTUtil.generateAccessToken(claims);

        // refreshToken 유효 시간 1시간 이하면 새로 발급
        String newRefreshToken = shouldRenewRefreshToken((Integer) claims.get("exp"))
            ? JWTUtil.generateRefreshToken(claims)
            : refreshToken;

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    private boolean isTokenExpired(String token) {
        try {
            JWTUtil.validateToken(token);
            return false;
        } catch (CustomJWTException e) {
            return "Expired".equals(e.getMessage());
        }
    }

    private boolean shouldRenewRefreshToken(Integer exp) {
        long expTime = (long) exp * 1000;
        long remainingMillis = expTime - System.currentTimeMillis();
        long remainingMinutes = remainingMillis / (1000 * 60);
        return remainingMinutes < 60;
    }
}
