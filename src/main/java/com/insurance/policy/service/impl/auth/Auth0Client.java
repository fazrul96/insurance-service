package com.insurance.policy.service.impl.auth;

import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.response.Auth0TokenResponse;
import com.insurance.policy.properties.AuthProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class Auth0Client {
    private final AuthProperties authProperties;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Auth0TokenResponse fetchToken() {
        String url = authProperties.getIssuer() + ApiConstant.AUTH0.TOKEN_URL;
        HttpEntity<Map<String, String>> request = buildRequest();

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.error("Failed to retrieve Auth0 token. Status: {}, Body: {}",
                    response.getStatusCode(), response.getBody());
            throw new RuntimeException("Failed to retrieve Auth0 token: HTTP " + response.getStatusCode());
        }

        return parseResponse(response.getBody());
    }

    private HttpEntity<Map<String, String>> buildRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "grant_type", authProperties.getGrantType(),
                "client_id", authProperties.getClientId(),
                "client_secret", authProperties.getClientSecret(),
                "audience", authProperties.getAudience()
        );

        return new HttpEntity<>(body, headers);
    }

    private Auth0TokenResponse parseResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, Auth0TokenResponse.class);
        } catch (Exception e) {
            log.error("Failed to parse Auth0 token response: {}", responseBody, e);
            throw new RuntimeException("Failed to parse Auth0 token response", e);
        }
    }
}
