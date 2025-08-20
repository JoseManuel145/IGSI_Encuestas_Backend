package com.igsi.encuestas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final JwtAuthenticationFilter jwtFilter;
    public SecurityConfig(JwtAuthenticationFilter jwtFilter, CorsConfig corsConfig) {
        this.jwtFilter = jwtFilter;
        this.corsConfig = corsConfig;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🔹 Desactiva CSRF (para pruebas con Postman)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // 🔹 Permite acceso libre a tu API
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {}); // opcional: habilita login básico en /login
        return http.build();
    }
}
