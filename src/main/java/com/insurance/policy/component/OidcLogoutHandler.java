package com.insurance.policy.component;

import com.insurance.policy.properties.AuthProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OidcLogoutHandler implements LogoutHandler {
    private final AuthProperties authProperties;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String redirectUrl = UriComponentsBuilder
                .fromUriString(authProperties.getIssuer())
                .path("/v2/logout")
                .queryParam("client_id", authProperties.getClientId())
                .queryParam("returnTo", resolveBaseUrl(request))
                .build()
                .toUriString();

        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new IllegalStateException("OIDC logout redirect failed", e);
        }
    }

    private String resolveBaseUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder
                .fromRequest(request)
                .replacePath(null)
                .build()
                .toUriString();
    }
}