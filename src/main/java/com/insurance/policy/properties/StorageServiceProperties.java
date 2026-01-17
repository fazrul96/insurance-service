package com.insurance.policy.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "storage-service")
public class StorageServiceProperties {
    private String baseUrl;
}