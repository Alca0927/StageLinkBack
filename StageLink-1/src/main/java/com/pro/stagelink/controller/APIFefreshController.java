package com.pro.stagelink.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Map<String, Object> refresh(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody Map<String, String> body
    ) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new CustomJWTException("NULL_REFRESH");
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomJWTException("INVALID_TOKEN_FORMAT");
        }

        // ✅ 기존 accessToken 유효성 검사는 제거
        // String accessToken = authHeader.substring(7);
        // if (!isTokenExpired(accessToken)) {
        //     return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        // }

        // refreshToken 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        if (!"refresh".equals(claims.get("tokenType"))) {
            throw new CustomJWTException("NOT_REFRESH_TOKEN");
        }

        log.info("refresh...claims: {}", claims);

        // 항상 새로운 accessToken 및 refreshToken 발급
        String newAccessToken = JWTUtil.generateAccessToken(claims);
        String newRefreshToken = JWTUtil.generateRefreshToken(claims);

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }
}
