package com.insurance.policy.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "insurance-service")
public class InsuranceWebClientConfiguration {
    private String baseUrl;

    @Bean(name = "insuranceWebClient")
    public WebClient insuranceWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(30 * 1024 * 1024))
                .build();
    }
}