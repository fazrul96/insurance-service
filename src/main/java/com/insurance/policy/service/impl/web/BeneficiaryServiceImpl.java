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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.insurance.policy.constants.GeneralConstant.MAX_BENEFICIARIES;
import static com.insurance.policy.dto.request.NotificationRequestDto.buildNotification;
import static com.insurance.policy.util.enums.NotificationTemplate.BENEFICIARY_CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {
    private final BeneficiaryRepository beneficiaryRepository;
    private final PolicyRepository policyRepository;
    private final NotificationServiceImpl notificationService;

    public BeneficiaryListResponseDto getBeneficiaries(String requestId) {
        log.info("[RequestId: {}] Execute BeneficiaryServiceImpl.getBeneficiaries()", requestId);

        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        return new BeneficiaryListResponseDto(beneficiaries);
    }

    public BeneficiaryListResponseDto getBeneficiariesByPolicyNo(String requestId, String policyNo) {
        log.info("[RequestId: {}] Execute BeneficiaryServiceImpl.getBeneficiariesByPolicyNo()", requestId);

        List<Beneficiary> beneficiaries = beneficiaryRepository.findByPolicyNo(policyNo);
        return new BeneficiaryListResponseDto(beneficiaries);
    }

    public BeneficiaryListResponseDto getBeneficiariesByPolicyId(String requestId, Long id) {
        log.info("[RequestId: {}] Execute BeneficiaryServiceImpl.getBeneficiariesByPolicyId()", requestId);
        return new BeneficiaryListResponseDto(beneficiaryRepository.findByPolicyId(id));
    }

    public Beneficiary createBeneficiary(String requestId, Beneficiary request) {
        log.info("[RequestId: {}] Execute BeneficiaryServiceImpl.createBeneficiary()", requestId);
        return beneficiaryRepository.save(request);
    }

    public BeneficiaryResponseDto createBeneficiaries(String requestId, String userId, BeneficiaryRequestDto request)
            throws WebException {
        log.info("[RequestId: {}] Execute BeneficiaryServiceImpl.createBeneficiaries()", requestId);

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
        return mapBeneficiaryResponseDto(request);
    }

    private Beneficiary getBeneficiaryById(String requestId, Long id) throws WebException {
        log.info("[RequestId: {}] Execute BeneficiaryServiceImpl.getBeneficiaryById()", requestId);
        return beneficiaryRepository.findById(id)
                .orElseThrow(() -> new WebException("Beneficiary not found"));
    }

    private BeneficiaryResponseDto mapBeneficiaryResponseDto(BeneficiaryRequestDto request) {
        return BeneficiaryResponseDto.builder()
                .policyNo(request.getPolicyNo())
                .beneficiaries(request.getBeneficiaries())
                .build();
    }

    private Beneficiary mapBeneficiary(BeneficiaryDto request, Long policyId) {
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
        beneficiaryRepository.save(mapBeneficiary(beneficiaryDto, policyId));
    }

    private void deleteBeneficiaries(Long id) {
        beneficiaryRepository.deleteById(id);
    }

    private void validateBeneficiaries(List<BeneficiaryDto> beneficiaries, Policy policy) throws WebException {

        Map<Long, Beneficiary> existingMap =
                beneficiaryRepository.findByPolicyNo(policy.getPolicyNo()).stream()
                        .collect(Collectors.toMap(Beneficiary::getId, b -> b));

        int activeCount = 0;
        int finalTotalShare = 0;

        for (BeneficiaryDto dto : beneficiaries) {
            ensureActionIsNotNull(dto);

            switch (dto.getAction()) {
                case CREATE -> {
                    validateCreate(dto);
                    finalTotalShare += dto.getShare().intValue();
                    activeCount++;
                }
                case UPDATE -> {
                    validateUpdate(dto, existingMap);
                    finalTotalShare += dto.getShare().intValue();
                }
                case DELETE -> {
                    validateDelete(dto, existingMap, policy);
                    activeCount--;
                }
                default -> throw new WebException("Invalid action for beneficiary: " + dto.getAction());
            }
        }

        validateTotalShare(finalTotalShare);
        validateMaxCount(activeCount);
    }

    private void ensureActionIsNotNull(BeneficiaryDto dto) throws WebException {
        if (dto.getAction() == null) {
            throw new WebException("Action cannot be null for beneficiary with ID: " + dto.getId());
        }
    }

    private void validateCreate(BeneficiaryDto dto) throws WebException {
        if (dto.getShare() == null || dto.getShare() <= 0) {
            throw new WebException("Share must be > 0 for CREATE action");
        }
    }

    private void validateUpdate(BeneficiaryDto dto, Map<Long, Beneficiary> existingMap) throws WebException {
        ensureIdPresent(dto, "UPDATE");

        if (!existingMap.containsKey(dto.getId())) {
            throw new WebException("Beneficiary not found for ID: " + dto.getId());
        }

        if (dto.getShare() == null || dto.getShare() <= 0) {
            throw new WebException("Share must be > 0 for UPDATE action (ID: " + dto.getId() + ")");
        }
    }

    private void validateDelete(BeneficiaryDto dto, Map<Long, Beneficiary> existingMap, Policy policy) throws WebException {
        ensureIdPresent(dto, "DELETE");

        if (!existingMap.containsKey(dto.getId())) {
            throw new WebException(
                    "Beneficiary with ID " + dto.getId() +
                            " not found in policy " + policy.getPolicyNo()
            );
        }
    }

    private void ensureIdPresent(BeneficiaryDto dto, String action) throws WebException {
        if (dto.getId() == null) {
            throw new WebException(action + " action requires an ID.");
        }
    }

    public void validateTotalShare(int totalShare) throws WebException {
        if (totalShare > 100) {
            throw new WebException("Total share exceeds 100% (current: " + totalShare + "%)");
        }
    }

    public void validateMaxCount(int count) throws WebException {
        if (count > MAX_BENEFICIARIES) {
            throw new WebException("Cannot exceed the maximum number of beneficiaries (" + MAX_BENEFICIARIES + ")");
        }
    }

    private Policy getPolicyByPolicyNo(String requestId, String policyNo) throws WebException {
        log.info("[RequestId: {}] Execute BeneficiaryServiceImpl.getPolicyByPolicyNo()", requestId);
        return policyRepository.findByPolicyNo(policyNo)
                .orElseThrow(() -> new WebException("Policy Number not found"));
    }
}