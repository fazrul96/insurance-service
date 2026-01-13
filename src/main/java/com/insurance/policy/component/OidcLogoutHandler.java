package com.insurance.policy.component;

import com.insurance.policy.properties.AuthProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        try {
            String baseUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .build()
                    .toUriString();

            String redirectUrl = authProperties.getIssuer()
                    + "v2/logout?client_id="
                    + authProperties.getClientId()
                    + "&returnTo=" + baseUrl;

            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new IllegalStateException("OIDC logout failed", e);
        }
    }
}