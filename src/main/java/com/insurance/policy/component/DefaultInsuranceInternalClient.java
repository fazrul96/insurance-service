package com.insurance.policy.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultInsuranceInternalClient implements InsuranceInternalClient {
    private final WebClient webClient;

    public DefaultInsuranceInternalClient(@Qualifier("insuranceInternalWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public WebClient client() {
        return webClient;
    }
}