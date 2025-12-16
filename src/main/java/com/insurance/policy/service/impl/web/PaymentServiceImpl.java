package com.insurance.policy.service.impl.web;

import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants.ResponseMessages;
import com.insurance.policy.data.entity.Payment;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.data.repository.PaymentRepository;
import com.insurance.policy.dto.PaymentDetailsDto;
import com.insurance.policy.dto.request.PaymentRequestDto;
import com.insurance.policy.dto.response.PaymentResponseDto;
import com.insurance.policy.dto.response.PaymentSummaryResponseDto;
import com.insurance.policy.service.PaymentService;
import com.insurance.policy.service.QuotationApplicationService;
import com.insurance.policy.util.common.LogUtils;
import com.insurance.policy.util.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.insurance.policy.dto.request.NotificationRequestDto.buildNotification;
import static com.insurance.policy.util.common.StringUtils.generateReferenceNumber;
import static com.insurance.policy.util.enums.NotificationTemplate.PAYMENT_FAILED;
import static com.insurance.policy.util.enums.NotificationTemplate.PAYMENT_SUCCESS;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PolicyServiceImpl policyService;
    private final QuotationApplicationService quotationApplicationService;
    private final NotificationServiceImpl notificationService;
    private final LogUtils logUtils;

    @Override
    public String getServiceName() {
        return "PaymentServiceImpl";
    }

    @Override
    public PaymentSummaryResponseDto getAllPayments(String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "getAllPayments");

        List<PaymentDetailsDto> response = paymentRepository.findAll()
                .stream()
                .map(this::mapPaymentDetails)
                .toList();

        return new PaymentSummaryResponseDto(response);
    }

    @Override
    public PaymentSummaryResponseDto getPaymentsByStatus(String requestId, String status) {
        logUtils.logRequest(requestId, getServiceName() + "getPaymentsByStatus");

        List<PaymentDetailsDto> response = paymentRepository.findByPaymentStatus(status)
                .stream()
                .map(this::mapPaymentDetails)
                .toList();

        return new PaymentSummaryResponseDto(response);
    }

    @Override
    public PaymentResponseDto paymentProcess(String userId, PaymentRequestDto requestDto, String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "paymentProcess");

        if (!PaymentStatus.SUCCESS.equals(requestDto.getPaymentStatus())) {
            policyService.updateStatusAndPayment(
                    requestDto.getQuotationId(), ResponseMessages.FAILURE, null, requestId);

            notificationService.notifyUser(buildNotification(null, 1L, PAYMENT_FAILED));
            return PaymentResponseDto.failure("Payment failed, application marked as FAILED.");
        }

        Payment payment = createPayment(mapPayment(requestDto));
        PaymentDetailsDto paymentDetailsDto = mapPaymentDetails(payment);

        QuotationApplication quotationApplication = quotationApplicationService
                .getQuotationsById(requestId, requestDto.getQuotationId());

        PaymentResponseDto responseDto = policyService.processPolicyPayment(
                requestDto, payment, quotationApplication, paymentDetailsDto, requestId
        );

        notificationService.notifyUser(buildNotification(userId, 1L, PAYMENT_SUCCESS));
        return responseDto;
    }

    private PaymentDetailsDto mapPaymentDetails(Payment request) {
        return PaymentDetailsDto.builder()
                .paymentId(request.getId())
                .paymentDate(request.getPaymentDate())
                .paymentAmount(request.getPaymentAmount())
                .paymentStatus(request.getPaymentStatus())
                .paymentReferenceNumber(request.getReferenceNumber())
                .build();
    }

    private Payment mapPayment(PaymentRequestDto request) {
        return Payment.builder()
                .paymentDate(new Date())
                .paymentAmount(request.getPaymentAmount())
                .paymentStatus(GeneralConstant.STATUS.COMPLETED)
                .duration(request.getDuration())
                .referenceNumber(generateReferenceNumber("T"))
                .quotationApplication(request.getQuotationId())
                .build();
    }

    private Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}