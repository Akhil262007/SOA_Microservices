package com.example.course.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/error").permitAll()

                        // Specific endpoint FIRST
                        .requestMatchers(
                                HttpMethod.POST,
                                "/courses/*/reserve-seat")
                        .hasRole("STUDENT")

                        // Course creation
                        .requestMatchers(
                                HttpMethod.POST,
                                "/courses/**")
                        .hasAnyRole("INSTRUCTOR", "ADMIN")

                        // Course read
                        .requestMatchers(
                                HttpMethod.GET,
                                "/courses/**")
                        .hasAnyRole(
                                "STUDENT", "INSTRUCTOR", "ADMIN")

                        // Course update
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/courses/**")
                        .hasAnyRole("INSTRUCTOR", "ADMIN")

                        // Course delete
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/courses/**")
                        .hasAnyRole("INSTRUCTOR", "ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}