package com.insurance.policy.service;

import com.insurance.policy.dto.BeneficiaryDto;

import java.util.Map;

public interface ValidationService {
    void validateBeneficiaryIdNotNull(BeneficiaryDto beneficiaryDto, String action);

    void validateBeneficiaryActionNotNull(BeneficiaryDto beneficiaryDto);

    <T> void validateEntityExists(Long id, Map<Long, T> existingMap, String entityName);

    void validateBeneficiaryShare(BeneficiaryDto beneficiaryDto);

    void validateMaxCount(int count);

    void validateTotalShare(int totalShare);
}