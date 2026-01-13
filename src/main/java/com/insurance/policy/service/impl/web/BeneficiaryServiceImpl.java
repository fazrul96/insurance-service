package com.insurance.policy.service.impl.web;

import com.insurance.policy.data.entity.Beneficiary;
import com.insurance.policy.data.entity.Policy;
import com.insurance.policy.data.repository.BeneficiaryRepository;
import com.insurance.policy.data.repository.PolicyRepository;
import com.insurance.policy.dto.BeneficiaryDto;
import com.insurance.policy.dto.request.BeneficiaryRequestDto;
import com.insurance.policy.dto.response.BeneficiaryListResponseDto;
import com.insurance.policy.dto.response.BeneficiaryResponseDto;
import com.insurance.policy.exception.WebException;
import com.insurance.policy.service.BeneficiaryService;
import com.insurance.policy.service.NotificationService;
import com.insurance.policy.service.ValidationService;
import com.insurance.policy.util.common.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.insurance.policy.constants.GeneralConstant.LOG4j.*;
import static com.insurance.policy.dto.request.NotificationRequestDto.buildNotification;
import static com.insurance.policy.util.enums.NotificationTemplate.BENEFICIARY_CREATED;

@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {
    private final BeneficiaryRepository beneficiaryRepository;
    private final PolicyRepository policyRepository;
    private final NotificationService notificationService;
    private final ValidationService validationService;
    private final LogUtils logUtils;

    @Override
    public String getServiceName() {
        return "BeneficiaryServiceImpl";
    }

    @Override
    public BeneficiaryListResponseDto getBeneficiaries(String requestId) {
        logUtils.logRequest(requestId, getServiceName() + GET_BENEFICIARIES);
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        return new BeneficiaryListResponseDto(beneficiaries);
    }

    @Override
    public BeneficiaryListResponseDto getBeneficiariesByPolicyNo(String requestId, String policyNo) {
        logUtils.logRequest(requestId, getServiceName() + GET_BENEFICIARIES_BY_POLICY_NO);
        List<Beneficiary> beneficiaries = beneficiaryRepository.findByPolicyNo(policyNo);
        return new BeneficiaryListResponseDto(beneficiaries);
    }

    @Override
    public BeneficiaryListResponseDto getBeneficiariesByPolicyId(String requestId, Long id) {
        logUtils.logRequest(requestId, getServiceName() + GET_BENEFICIARIES_BY_POLICY_ID);
        return new BeneficiaryListResponseDto(beneficiaryRepository.findByPolicyId(id));
    }

    @Override
    public List<BeneficiaryDto> getBeneficiaryListByPolicyId(String requestId, Long id) {
        logUtils.logRequest(requestId, getServiceName() + GET_BENEFICIARY_LIST_BY_POLICY_ID);
        List<Beneficiary> beneficiaries = beneficiaryRepository.findByPolicyId(id);

        return beneficiaries.stream()
                .map(b -> BeneficiaryDto.builder()
                        .id(b.getId())
                        .policyId(b.getPolicyId())
                        .beneficiaryName(b.getBeneficiaryName())
                        .relationshipToInsured(b.getRelationshipToInsured())
                        .share(b.getShare())
                        .build()
                )
                .toList();
    }

    @Override
    public Beneficiary createBeneficiary(String requestId, Beneficiary request) {
        logUtils.logRequest(requestId, getServiceName() + CREATE_BENEFICIARY);
        return beneficiaryRepository.save(request);
    }

    @Override
    public BeneficiaryResponseDto createBeneficiaries(String requestId, String userId, BeneficiaryRequestDto request) {
        logUtils.logRequest(requestId, getServiceName() + CREATE_BENEFICIARIES);

        Policy policy = getPolicyByPolicyNo(requestId, request.getPolicyNo());
        List<BeneficiaryDto> beneficiaries = request.getBeneficiaries();

        validateBeneficiaries(beneficiaries, policy);

        for (BeneficiaryDto beneficiaryDto : beneficiaries) {
            switch (beneficiaryDto.getAction()) {
                case CREATE:
                    saveBeneficiaries(beneficiaryDto, policy.getId());
                    break;
                case UPDATE:
                    Beneficiary existingBen = getBeneficiaryById(requestId, beneficiaryDto.getId());
                    updateBeneficiary(existingBen, beneficiaryDto);
                    break;
                case DELETE:
                    deleteBeneficiaries(beneficiaryDto.getId());
                    break;
                default:
                    throw new WebException("Invalid action for beneficiary: " + beneficiaryDto.getAction());
            }
        }

        notificationService.notifyUser(buildNotification(userId, null, BENEFICIARY_CREATED));
        return toBeneficiaryResponseDto(request);
    }

    private Beneficiary getBeneficiaryById(String requestId, Long id) {
        logUtils.logRequest(requestId, getServiceName() + "getBeneficiaryById");
        return beneficiaryRepository.findById(id)
                .orElseThrow(() -> new WebException("Beneficiary not found"));
    }

    private BeneficiaryResponseDto toBeneficiaryResponseDto(BeneficiaryRequestDto request) {
        return BeneficiaryResponseDto.builder()
                .policyNo(request.getPolicyNo())
                .beneficiaries(request.getBeneficiaries())
                .build();
    }

    private Beneficiary toBeneficiary(BeneficiaryDto request, Long policyId) {
        return Beneficiary.builder()
                .policyId(policyId)
                .beneficiaryName(request.getBeneficiaryName())
                .relationshipToInsured(request.getRelationshipToInsured())
                .share(request.getShare())
                .build();
    }

    private void updateBeneficiary(Beneficiary existingBen, BeneficiaryDto request) {
        existingBen.setBeneficiaryName(request.getBeneficiaryName());
        existingBen.setRelationshipToInsured(request.getRelationshipToInsured());
        existingBen.setShare(request.getShare());
        beneficiaryRepository.save(existingBen);
    }

    private void saveBeneficiaries(BeneficiaryDto beneficiaryDto, Long policyId) {
        beneficiaryRepository.save(toBeneficiary(beneficiaryDto, policyId));
    }

    private void deleteBeneficiaries(Long id) {
        beneficiaryRepository.deleteById(id);
    }

    private void validateBeneficiaries(List<BeneficiaryDto> beneficiaries, Policy policy) {

        Map<Long, Beneficiary> existingMap =
                beneficiaryRepository.findByPolicyNo(policy.getPolicyNo()).stream()
                        .collect(Collectors.toMap(Beneficiary::getId, b -> b));

        int activeCount = 0;
        int finalTotalShare = 0;

        for (BeneficiaryDto dto : beneficiaries) {
            validationService.validateBeneficiaryActionNotNull(dto);

            switch (dto.getAction()) {
                case CREATE -> {
                    validationService.validateBeneficiaryShare(dto);
                    finalTotalShare += dto.getShare().intValue();
                    activeCount++;
                }
                case UPDATE -> {
                    validateUpdate(dto, existingMap);
                    finalTotalShare += dto.getShare().intValue();
                }
                case DELETE -> {
                    validateDelete(dto, existingMap);
                    activeCount--;
                }
                default -> throw new WebException("Invalid action for beneficiary: " + dto.getAction());
            }
        }

        validationService.validateTotalShare(finalTotalShare);
        validationService.validateMaxCount(activeCount);
    }

    private void validateUpdate(BeneficiaryDto dto, Map<Long, Beneficiary> existingMap) {
        validationService.validateBeneficiaryIdNotNull(dto, "UPDATE");
        validationService.validateEntityExists(dto.getId(), existingMap, "Beneficiary");
        validationService.validateBeneficiaryShare(dto);
    }

    private void validateDelete(BeneficiaryDto dto, Map<Long, Beneficiary> existingMap) {
        validationService.validateBeneficiaryIdNotNull(dto, "DELETE");
        validationService.validateEntityExists(dto.getId(), existingMap, "Beneficiary");
    }

    private Policy getPolicyByPolicyNo(String requestId, String policyNo) {
        logUtils.logRequest(requestId, getServiceName() + "getPolicyByPolicyNo");
        return policyRepository.findByPolicyNo(policyNo)
                .orElseThrow(() -> new WebException("Policy Number not found"));
    }
}