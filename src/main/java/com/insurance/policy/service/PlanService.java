package com.insurance.policy.service;

import com.insurance.policy.data.entity.Plan;
import com.insurance.policy.dto.request.PlanRequestDto;
import com.insurance.policy.dto.response.PlanResponseDto;
import com.insurance.policy.exception.WebException;

public interface PlanService {
    Plan getPlan(String requestId, Long id) throws WebException;
    PlanResponseDto generatePlan(PlanRequestDto request, String requestId);
}