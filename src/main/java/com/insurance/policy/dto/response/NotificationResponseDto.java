package com.insurance.policy.dto.response;

import com.insurance.policy.data.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
    private List<Notification> notificationList = new ArrayList<>();
}