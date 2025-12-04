package com.insurance.policy.data.entity;

import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.NOTIFICATION)
public class Notification extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String message;
    private boolean isRead;
    private String actionType;         // e.g., CREATE_BENEFICIARY, DELETE_PLAN
    private String redirectUrl;        // URL to redirect user when they click on the notification
    private String notificationType;   // e.g., INFO, ERROR, SUCCESS
    private Long relatedEntityId;      // ID of the related entity, like beneficiary_id, plan_id, etc.
    private String status;  // Notification status (UNREAD, READ, DELETED)
}
