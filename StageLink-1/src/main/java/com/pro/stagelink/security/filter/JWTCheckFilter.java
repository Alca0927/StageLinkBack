package com.pro.stagelink.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.pro.stagelink.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		log.info("--------------JWTCheckFilter-------------");
		String authHeaderStr = request.getHeader("Authorization");
		try {
			// GPT 추천 (Authorization 헤더가 null의 경우)
			if(authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
			    filterChain.doFilter(request, response);
			    return;
			}
			// GPT 추천 끝
			String accessToken = authHeaderStr.substring(7);
			Map<String, Object> claims = JWTUtil.validateToken(accessToken);
			log.info("JWT claims: " + claims);
			
			//???
			String username = (String) claims.get("username");
			List<String> roles = (List<String>) claims.get("roles");
			
			var authorities = roles.stream()
				    .map(role -> new SimpleGrantedAuthority(role))  // ex: ROLE_ADMIN
				    .toList();
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
			
			SecurityContextHolder.getContext()
			.setAuthentication (authenticationToken);

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.error("JWT Check Error----------------");
			log.error(e.getMessage());
			Gson gson = new Gson();
			String msg = gson.toJson(Map.of("error","ERROR_ACCESS_TOKEN"));
			response.setContentType("application/json");
			PrintWriter printWriter = response.getWriter();
			printWriter.println(msg);
			printWriter.close();
		}
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
		
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}
		
		String path = request.getRequestURI();
		log.info("Check Uri-----------------------" + path);
		
		if(path.startsWith("/api/login")) {
			return true;
		}
		
		return false;
	}
}
