package com.insurance.policy.component;

import com.insurance.policy.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import static com.insurance.policy.constants.GeneralConstant.DOUBLE_ASTERISKS;
import static com.insurance.policy.constants.SecurityConstant.ADDITIONAL_PATHS;

@Component
@RequiredArgsConstructor
public class AuthorizationPolicy {
    private final AppProperties appProperties;

    public void configureAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(ADDITIONAL_PATHS).permitAll()
                .requestMatchers(appProperties.getPublicApiPath() + DOUBLE_ASTERISKS).permitAll()
                .requestMatchers(appProperties.getPrivateApiPath() + DOUBLE_ASTERISKS).authenticated()
                .anyRequest().authenticated();
    }
}