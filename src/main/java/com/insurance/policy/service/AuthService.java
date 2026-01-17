package com.insurance.policy.service;

import com.insurance.policy.data.entity.User;
import com.insurance.policy.dto.request.AuthRequestDto;
import com.insurance.policy.dto.response.AuthResponseDto;

public interface AuthService {
    AuthResponseDto register(String requestId, User user);

    AuthResponseDto login (String requestId, AuthRequestDto request);

    AuthResponseDto loginAuth0 (String requestId, User request);

    String fetchUserAccessToken();

    String getServiceName();
}
