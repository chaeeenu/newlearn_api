package com.newlearn.client.config;

import com.newlearn.auth.security.CustomAuthenticationEntryPoint;
import com.newlearn.auth.security.JwtAuthenticationFilter;
import com.newlearn.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint entryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

         httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)     // formLogin 미사용
                .httpBasic(HttpBasicConfigurer::disable)        // httpBasic 미사용
                .csrf(AbstractHttpConfigurer::disable)          // csrf 비활성화
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // 세션 미사용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/user/**",     // 로그인,회원가입 등
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/oauth2/**"    // OAuth2 관련 URL
                        ).permitAll()
                        .anyRequest().authenticated()) // 명시한 URL 외 나머지요청은 인증필요
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))   // 인증이 실패했을 때(토큰 없음, 만료 등), 클라이언트에게 JSON으로 에러를 응답하는 처리기
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtProvider),       // 요청에 담긴 JWT을 검사하고, 유효하면 인증을 완료하는 필터
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);   // 쿠키나 인증정보 포함 허용(필요 시)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
