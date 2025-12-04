package com.insurance.policy.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequestDto {
    private String userId;
    private String message;
    private String actionType;
    private String redirectUrl;
    private String notificationType;
    private Long relatedEntityId;

    public static NotificationRequestDto generic(
            String userId, String message, String actionType, String redirectUrl, String notificationType, Long id
    ) {
        return NotificationRequestDto.builder()
                .userId(userId)
                .message(message)
                .actionType(actionType)
                .redirectUrl(redirectUrl)
                .notificationType(notificationType)
                .relatedEntityId(id)
                .build();
    }
}