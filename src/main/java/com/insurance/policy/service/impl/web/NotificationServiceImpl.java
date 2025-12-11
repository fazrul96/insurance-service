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

    @Override
    public void notifyUser(NotificationRequestDto request) {
        Notification notification = toNotification(request);
        notificationRepository.save(notification);
    }

    @Override
    public NotificationResponseDto getUserNotifications(String userId, String requestId) {
        log.info("[RequestId: {}] Execute NotificationServiceImpl.getUserNotifications()", requestId);

        List<Notification> response = notificationRepository.findByUserId(userId);
        return new NotificationResponseDto(response);
    }

    private Notification toNotification(NotificationRequestDto request) {
        return Notification.builder()
                .userId(request.getUserId())
                .message(request.getMessage())
                .isRead(request.isRead())
                .actionType(request.getEventType().toString())
                .redirectUrl(request.getRedirectUrl())
                .notificationType(request.getSeverity().toString())
                .relatedEntityId(request.getRelatedEntityId())
                .status(request.getStatus())
                .build();
    }
}