package com.insurance.policy.dto.request;

import com.insurance.policy.util.enums.NotificationEventType;
import com.insurance.policy.util.enums.NotificationSeverity;
import com.insurance.policy.util.enums.NotificationTemplate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequestDto {
    private String userId;
    private String message;
    private NotificationEventType eventType;
    private NotificationSeverity severity;
    private String redirectUrl;
    private Long relatedEntityId;
    private boolean isRead;
    private String status;

    public static NotificationRequestDto buildNotification(
            String userId, Long entityId, NotificationTemplate template
    ) {
        String redirectUrl = entityId == null
                ? template.getPath()
                : template.getPath() + entityId;

        return NotificationRequestDto.builder()
                .userId(userId)
                .message(template.getMessageKey())
                .eventType(template.getEventType())
                .redirectUrl(redirectUrl)
                .severity(template.getSeverity())
                .relatedEntityId(entityId)
                .isRead(false)
                .status("NEW")
                .build();
    }
}