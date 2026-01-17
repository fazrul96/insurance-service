package com.insurance.policy.component;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InternalServiceAuthFilter implements ExchangeFilterFunction {
    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    private final InternalAuthTokenProvider tokenProvider;

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        if (request.headers().containsKey(AUTH_HEADER)) {
            return next.exchange(request);
        }

        ClientRequest authenticatedRequest = ClientRequest.from(request)
                .header(AUTH_HEADER, tokenProvider.getAuthorizationHeader())
                .build();

        return next.exchange(authenticatedRequest);
    }
}