package com.insurance.policy.service.impl.web;

import com.insurance.policy.constants.MessageConstants.ResponseMessages;
import com.insurance.policy.data.entity.Payment;
import com.insurance.policy.data.entity.Plan;
import com.insurance.policy.data.entity.Policy;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.data.repository.PolicyRepository;
import com.insurance.policy.dto.PaymentDetailsDto;
import com.insurance.policy.dto.PlanInfoDto;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.request.PaymentRequestDto;
import com.insurance.policy.dto.response.PaymentResponseDto;
import com.insurance.policy.dto.response.PolicyResponseDto;
import com.insurance.policy.dto.response.PolicySummaryResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;
import com.insurance.policy.exception.WebException;
import com.insurance.policy.service.NotificationService;
import com.insurance.policy.service.PolicyService;
import com.insurance.policy.service.QuotationApplicationService;
import com.insurance.policy.util.common.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.insurance.policy.dto.request.NotificationRequestDto.buildNotification;
import static com.insurance.policy.util.common.StringUtils.generateReferenceNumber;
import static com.insurance.policy.util.enums.NotificationTemplate.QUOTATION_CREATED;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private final PolicyRepository policyRepository;
    private final QuotationApplicationService quotationApplicationService;
    private final NotificationService notificationService;
    private final LogUtils logUtils;
    private static final long ONE_YEAR_MILLIS = 31_536_000_000L;

    @Override
    public String getServiceName() {
        return "PolicyServiceImpl";
    }

    @Override
    public PolicySummaryResponseDto getAllPolicies(String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "getAllPolicies");
        List<PolicyResponseDto> policies = policyRepository.findAll()
                .stream()
                .map(this::toPolicyResponse)
                .toList();

        return new PolicySummaryResponseDto(policies);
    }

    @Override
    public PolicySummaryResponseDto getPolicyByUserId(Long userId, String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "getPolicyByUserId");
        List<PolicyResponseDto> policies = policyRepository.findByUserId(userId)
                .stream()
                .map(this::toPolicyResponse)
                .toList();

        return new PolicySummaryResponseDto(policies);
    }

    @Override
    public PolicySummaryResponseDto getPolicyByUserKey(String requestId, String userId) {
        logUtils.logRequest(requestId, getServiceName() + "getPolicyByUserKey");
        List<PolicyResponseDto> policies = policyRepository.findByUserKey(userId)
                .stream()
                .map(this::toPolicyResponse)
                .toList();

        return new PolicySummaryResponseDto(policies);
    }

    @Override
    public PolicyResponseDto getPolicyById(String requestId, Long id) {
        logUtils.logRequest(requestId, getServiceName() + "getPolicyById");
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new WebException("Policy not found"));

        return toPolicyResponse(policy);
    }

    public Policy findPolicyById(String requestId, Long id) {
        logUtils.logRequest(requestId, getServiceName() + "findPolicyById");
        return policyRepository.findById(id)
                .orElseThrow(() -> new WebException("Policy not found"));
    }

    @Override
    public PolicyResponseDto constructApplicationAndBeneficiaryResponse(Policy policy) {
        QuotationApplication quotationApplication = policy.getQuotationApplication();
        Plan plan = policy.getPlan();

        QuotationApplicationResponseDto application =
                quotationApplicationService.toQuotationApplicationResponse(quotationApplication);

        PlanInfoDto planInfoDto = toPlanInfo(plan, quotationApplication);
        PolicyResponseDto policyResponseDto = toPolicyResponse(policy);

        application.setPlanResponseDto(planInfoDto);
        policyResponseDto.setApplicationResponseDto(application);

        return policyResponseDto;
    }

    @Override
    public QuotationApplicationResponseDto createApplication(
            QuotationApplicationRequestDto requestDto, String userId, String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "createApplication");
        notificationService.notifyUser(buildNotification(requestId, null, QUOTATION_CREATED));
        return quotationApplicationService.processQuotation(requestId, userId, requestDto);
    }

    private Policy createPolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    public void updateStatusAndPayment(Long applicationId, String status, Payment payment, String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "updateStatusAndPayment");

        QuotationApplication application = quotationApplicationService.getQuotationsById(requestId, applicationId);

        application.setApplicationStatus(status);
        quotationApplicationService.createQuotation(application);
    }

    public PaymentResponseDto processPolicyPayment(
            PaymentRequestDto request, Payment payment, QuotationApplication application,
            PaymentDetailsDto paymentDetails, String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "processPolicyPayment");
        updateStatusAndPayment(request.getQuotationId(), ResponseMessages.SUCCESS, payment, requestId);

        Policy policy = createPolicy(toPolicy(application, payment));
        PolicyResponseDto policyResponseDto = constructApplicationAndBeneficiaryResponse(policy);

        return PaymentResponseDto.success(policyResponseDto, paymentDetails);
    }

    private PolicyResponseDto toPolicyResponse(Policy policy) {
        return PolicyResponseDto.builder()
                .id(policy.getId())
                .policyNo(policy.getPolicyNo())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .status(policy.getStatus())
                .build();
    }

    private PlanInfoDto toPlanInfo(Plan plan, QuotationApplication quotationApplication) {
        return PlanInfoDto.builder()
                .id(plan.getId())
                .planName(plan.getPlanName())
                .coverageTerm(plan.getDuration().toString().concat(" years"))
                .sumAssured(plan.getCoverageAmount())
                .premiumAmount(quotationApplication.getPremiumAmount())
                .premiumMode(quotationApplication.getPremiumMode())
                .referenceNumber(quotationApplication.getReferenceNumber())
                .build();
    }

    private Policy toPolicy(QuotationApplication application, Payment payment) {
        return Policy.builder()
                .policyNo(generateReferenceNumber("POL"))
                .startDate(new Date())
                .endDate(new Date(System.currentTimeMillis() + ONE_YEAR_MILLIS))
                .status("ACTIVE")
                .quotationApplication(application)
                .plan(application.getPlan())
                .user(application.getUser())
                .payment(payment)
                .build();
    }
}