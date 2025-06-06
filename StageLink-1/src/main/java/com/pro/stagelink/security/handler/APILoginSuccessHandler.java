package com.pro.stagelink.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;
import com.pro.stagelink.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        log.info("✅ Login Success: {}", authentication);

        // 무조건 Admin으로 고정
        String fixedUsername = "Admin";

        // ✅ claims 구성
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", fixedUsername);
        claims.put("roles", authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList()));

        // ✅ JWT 생성
        String accessToken = JWTUtil.generateAccessToken(claims);
        String refreshToken = JWTUtil.generateRefreshToken(claims);

        log.info("✅ AccessToken 생성: {}", accessToken);
        log.info("✅ RefreshToken 생성: {}", refreshToken);

        // ✅ 응답 JSON 구성
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        tokenMap.put("username", fixedUsername); // 역시 Admin 고정

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(new Gson().toJson(tokenMap));
        writer.flush();
        writer.close();
    }
}
