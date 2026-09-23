package com.example.gateway.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class AuthorizationDebugFilter {

    @Bean
    public WebFilter authorizationLogger() {

        return (exchange, chain) -> {

            String authorization = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            System.out.println(
                    "GATEWAY Authorization: " + authorization
            );

            return chain.filter(exchange);
        };
    }
}