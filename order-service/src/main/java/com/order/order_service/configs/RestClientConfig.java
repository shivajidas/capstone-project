package com.order.order_service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient productApiClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080") // Gateway URL
                .requestInterceptor(tokenPropagationInterceptor())
                .build();
    }

    @Bean
    public RestClient customerApiClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080") // Gateway URL
                .build();
    }

    private ClientHttpRequestInterceptor tokenPropagationInterceptor() {
        return (request, body, execution) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                String tokenValue = jwtAuth.getToken().getTokenValue();
                request.getHeaders().setBearerAuth(tokenValue);
            }
            return execution.execute(request, body);
        };
    }
}
