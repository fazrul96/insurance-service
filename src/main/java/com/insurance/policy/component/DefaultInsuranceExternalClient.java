package com.insurance.policy.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultInsuranceExternalClient implements InsuranceExternalClient {
    private final WebClient webClient;

    public DefaultInsuranceExternalClient(@Qualifier("insuranceExternalWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public WebClient client() {
        return webClient;
    }
}