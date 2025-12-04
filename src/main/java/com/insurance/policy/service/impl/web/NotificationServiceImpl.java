package com.insurance.policy.service.impl.web;

import com.insurance.policy.data.entity.Notification;
import com.insurance.policy.data.repository.NotificationRepository;
import com.insurance.policy.dto.request.NotificationRequestDto;
import com.insurance.policy.dto.response.NotificationResponseDto;
import com.insurance.policy.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public void notifyUser(NotificationRequestDto request) {
        Notification notification = mapNotification(request);
        notificationRepository.save(notification);
    }

    public NotificationResponseDto getUserNotifications(String userId, String requestId) {
        log.info("[RequestId: {}] Execute NotificationServiceImpl.getUserNotifications()", requestId);

        List<Notification> response = notificationRepository.findByUserId(userId);
        return new NotificationResponseDto(response);
    }

    private Notification mapNotification(NotificationRequestDto request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setMessage(request.getMessage());
        notification.setRead(false);
        notification.setActionType(request.getActionType());
        notification.setRedirectUrl(request.getRedirectUrl());
        notification.setNotificationType(request.getNotificationType());
        notification.setRelatedEntityId(request.getRelatedEntityId());
        notification.setStatus("UNREAD");

        return notification;
    }
}