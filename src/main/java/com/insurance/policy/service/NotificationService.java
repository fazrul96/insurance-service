package com.insurance.policy.service;

import com.insurance.policy.dto.request.NotificationRequestDto;
import com.insurance.policy.dto.response.NotificationResponseDto;

public interface NotificationService {

    void notifyUser(NotificationRequestDto request);

    NotificationResponseDto getUserNotifications(String userId, String requestId);
}
