package com.insurance.policy.service.impl.auth;

import com.insurance.policy.data.entity.User;
import com.insurance.policy.data.repository.UserRepository;
import com.insurance.policy.dto.request.AuthRequestDto;
import com.insurance.policy.dto.response.LoginResponseDto;
import com.insurance.policy.service.AuthService;
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

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String getServiceName() {
        return "AuthServiceImpl";
    }

    @Override
    public LoginResponseDto login (AuthRequestDto request) {
        authenticate(request);

        User user = findUserByEmail(request.getEmail());
        return mapLoginResponseDto(user, fetchUserAccessToken());
    }

    @Override
    @Transactional
    public LoginResponseDto loginAuth0(User request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            registerUser(user);
        }

        return mapLoginResponseDto(user, fetchUserAccessToken());
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(generateRandomPassword()));
        user.setUserId(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    private String generateRandomPassword() {
        byte[] bytes = new byte[12];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private LoginResponseDto mapLoginResponseDto(User user, String token) {
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setEmail(user.getEmail());
        responseDto.setToken(token);
        responseDto.setUsername(user.getUsername());
        responseDto.setUserId(user.getUserId());

        return responseDto;
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