package com.insurance.policy.service;

import com.insurance.policy.dto.request.PaymentRequestDto;
import com.insurance.policy.dto.response.PaymentResponseDto;
import com.insurance.policy.dto.response.PaymentSummaryResponseDto;

public interface PaymentService {

    PaymentSummaryResponseDto getAllPayments(String requestId);

    PaymentSummaryResponseDto getPaymentsByStatus(String requestId, String status);

    PaymentResponseDto paymentProcess(String userId, PaymentRequestDto requestDto, String requestId);
}
