package com.insurance.policy.service.impl.auth;

import com.insurance.policy.data.entity.User;
import com.insurance.policy.data.repository.UserRepository;
import com.insurance.policy.dto.request.AuthRequestDto;
import com.insurance.policy.dto.response.AuthResponseDto;
import com.insurance.policy.exception.WebException;
import com.insurance.policy.service.AuthService;
import com.insurance.policy.util.common.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final Auth0Client auth0Client;
    private final PasswordEncoder passwordEncoder;
    private final LogUtils logUtils;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String getServiceName() {
        return "AuthServiceImpl";
    }

    @Override
    public AuthResponseDto login (String requestId, AuthRequestDto request) {
        logUtils.logRequest(requestId, getServiceName() + "login");

        authenticate(request);

        User user = findUserByEmail(request.getEmail());
        return toAuthResponseDto(user, fetchUserAccessToken());
    }

    @Override
    @Transactional
    public AuthResponseDto loginAuth0(String requestId, User request) {
        logUtils.logRequest(requestId, getServiceName() + "loginAuth0");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseGet(() -> registerUser(requestId, request, true));

        return toAuthResponseDto(user, fetchUserAccessToken());
    }

    @Override
    public AuthResponseDto register(String requestId, User request) {
        logUtils.logRequest(requestId, getServiceName() + "register");

        return toAuthResponseDto(registerUser(requestId, request, false), fetchUserAccessToken());
    }

    private User registerUser(String requestId, User user, boolean isAuth0) {
        logUtils.logRequest(requestId, getServiceName() + "registerUser");

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new WebException("Email already exists!");
        }

        if (!isAuth0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(passwordEncoder.encode(generateRandomPassword()));
        }

        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private String generateRandomPassword() {
        byte[] bytes = new byte[12];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private AuthResponseDto toAuthResponseDto(User user, String token) {
        return AuthResponseDto.builder()
                .email(user.getEmail())
                .token(token)
                .userId(user.getUserId())
                .username(user.getUsername())
                .build();
    }

    private String fetchUserAccessToken() {
        return auth0Client.fetchToken().getAccessToken();
    }

    private void authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
    }
}