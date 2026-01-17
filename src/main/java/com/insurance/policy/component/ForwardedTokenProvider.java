package com.insurance.policy.component;

import com.insurance.policy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForwardedTokenProvider implements InternalAuthTokenProvider {
    private final AuthService authService;

    @Override
    public String getAuthorizationHeader() {
        return "Bearer " + authService.fetchUserAccessToken();
    }
}