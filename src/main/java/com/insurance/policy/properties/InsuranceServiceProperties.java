package com.insurance.policy.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "insurance.service")
public class InsuranceServiceProperties {
    private String baseUrl;
}