package com.insurance.policy.config;

import com.insurance.policy.component.InternalServiceAuthFilter;
import com.insurance.policy.properties.InsuranceServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(InsuranceServiceProperties.class)
public class InsuranceWebClientConfig {
    private final InternalServiceAuthFilter internalAuthFilter;
    private final InsuranceServiceProperties properties;

    @Bean("insuranceExternalWebClient")
    public WebClient insuranceExternalWebClient() {
        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(30 * 1024 * 1024))
                .build();
    }

    @Bean("insuranceInternalWebClient")
    public WebClient insuranceInternalWebClient() {
        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .filter(internalAuthFilter)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(30 * 1024 * 1024))
                .build();
    }
}