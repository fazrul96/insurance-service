package com.insurance.policy.config;

import com.insurance.policy.properties.AuthProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import static com.insurance.policy.constants.ApiConstant.AUTH0.JWKS_JSON;

@Configuration
public class JwtDecoderConfig {
    @Bean
    public JwtDecoder jwtDecoder(AuthProperties authProperties) {
        return NimbusJwtDecoder.withJwkSetUri(
                authProperties.getIssuer() + JWKS_JSON
        ).build();
    }
}