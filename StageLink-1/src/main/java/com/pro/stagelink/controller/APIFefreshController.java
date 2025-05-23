package com.pro.stagelink.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.util.CustomJWTException;
import com.pro.stagelink.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIFefreshController {
	@RequestMapping("/admin/api/login/refresh")
	public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken){
		if(refreshToken == null) {
			throw new CustomJWTException("NULL_REFRESH");
		}
		if (authHeader == null || authHeader.length() < 7) {
			throw new CustomJWTException("INVALID_STRING");
		}
		
		// ???
		if (!authHeader.startsWith("Bearer ")) {
		    throw new CustomJWTException("INVALID_TOKEN_FORMAT");
		}
		//
		String accessToken = authHeader.substring(7);
		// Access 토큰이 만료되지 않았다면
		if(checkExpiredToken(accessToken) == false) {
			return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
		}
		
		// Refresh 토큰 검증
		Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
		log.info("refresh ... claims" + claims);
		String newAccessToken = JWTUtil.generateToken(claims, 10);
		
		String newRefreshToken = checkTime((Integer) claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;
		return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
	}
	
	private boolean checkTime(Integer exp) {
		java.util.Date expDate = new java.util.Date((long) exp * (1000));
		long gap = expDate.getTime() - System.currentTimeMillis();
		long leftMin = gap/(1000 * 60);
		return leftMin < 60;
	}
	
	private boolean checkExpiredToken(String token) {
		try {
			JWTUtil.validateToken(token);
		} catch (CustomJWTException ex) {
			if (ex.getMessage().equals("Expired")) {
				return true;
			}
		}
		return false;
	}
}
