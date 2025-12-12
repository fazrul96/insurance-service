package com.insurance.policy.service;

import com.insurance.policy.data.entity.User;
import com.insurance.policy.dto.request.AuthRequestDto;
import com.insurance.policy.dto.response.LoginResponseDto;

public interface AuthService {
    void registerUser(User user);

    LoginResponseDto login (AuthRequestDto request);

    LoginResponseDto loginAuth0 (User request);

    String getServiceName();
}
