package com.pro.stagelink.config;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.pro.stagelink.security.filter.JWTCheckFilter;
import com.pro.stagelink.security.handler.APILoginFailHandler;
import com.pro.stagelink.security.handler.APILoginSuccessHandler;
import com.pro.stagelink.security.handler.CustomAccessDeniedHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomSecurityConfig {
	// 사용자 비밀번호의 단방향 암호화를 지원
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 관리자 정보 설정
	@Bean
	protected UserDetailsService Users() {
		UserDetails admin = User.builder()
				.username("Admin")
				.password(passwordEncoder().encode("1234"))
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(admin);
	}
	/*
	// Admin 권한이 있는 사용자만 경로 접근 가능
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests
				.requestMatchers("/admin/**").hasRole("ADMIN")
		.anyRequest().permitAll()
		).formLogin(
				formLogin -> formLogin
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/admin")
				.failureUrl("/loginfailed")
				.usernameParameter("username")
				.passwordParameter("password")
				)
		.logout(
				logout->logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
		);
		return http.build();
	}
	*/
	
	// 3. AuthenticationProvider 빈 등록 (UserDetailsService + PasswordEncoder 연결)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(Users());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 4. AuthenticationManager 빈 등록 (HttpSecurity 빌더에서 가져옴)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }
	
	
	
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("-----------------securityConfig-----------");
		
		http.cors(httpSecurityCorsConfigurer -> {
			httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
		});
		
		http.sessionManagement(sessionConfig -> 
		sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(config -> config.disable());
		
		// 🔒 경로별 권한 설정 추가
	    http.authorizeHttpRequests(auth -> auth
	        .requestMatchers("/", "/s/login", "/s/logout").permitAll()
	        .requestMatchers("/admin/**","/api/**").hasRole("ADMIN")  // 인증된 ADMIN만 허용
	        .anyRequest().authenticated()
	    );
		
		
		http.formLogin(config -> {
			config
			.loginProcessingUrl("/admin/api/login");
			config.successHandler(new APILoginSuccessHandler());
			config.failureHandler(new APILoginFailHandler());
		});
		// jWT 체크
		http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.exceptionHandling(config -> {
			config.accessDeniedHandler(new CustomAccessDeniedHandler());
		});
		return http.build();
	}
	
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD","GET","POST","PUT","DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
}
