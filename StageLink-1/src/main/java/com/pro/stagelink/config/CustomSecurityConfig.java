package com.pro.stagelink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomSecurityConfig {
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults()) // ✅ CORS 허용
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll() // API 엔드포인트 인증 없이 접근 허용
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
