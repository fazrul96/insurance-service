package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.NotificationResponseDto;
import com.insurance.policy.service.impl.web.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class NotificationController extends BaseController {
    private final NotificationServiceImpl notificationServiceImpl;

    @Operation(summary = "Fetch all notifications for the authenticated user")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.GET_NOTIFICATIONS)
    public ApiResponseDto<NotificationResponseDto>getAllNotifications(RequestContext context) {
        logRequest(context.getRequestId(), "NotificationController.getAllNotifications()");
        return handleRequest(context, () -> notificationServiceImpl.getUserNotifications(
                context.getUserId(), context.getRequestId())
        );
    }
}
