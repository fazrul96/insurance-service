package com.insurance.policy.component;

import org.springframework.web.reactive.function.client.WebClient;

public interface InsuranceInternalClient {
    WebClient client();
}