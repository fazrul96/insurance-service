package com.insurance.policy.service;

import com.insurance.policy.data.entity.Plan;
import com.insurance.policy.dto.request.PlanRequestDto;
import com.insurance.policy.dto.response.PlanResponseDto;

public interface PlanService {
    Plan getPlan(Long id);
    PlanResponseDto generatePlan(PlanRequestDto request, String requestId);
}