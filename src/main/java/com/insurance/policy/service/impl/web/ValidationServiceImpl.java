package com.insurance.policy.service.impl.web;

import com.insurance.policy.dto.BeneficiaryDto;
import com.insurance.policy.exception.WebException;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.insurance.policy.constants.GeneralConstant.MAX_BENEFICIARIES;

/**
 * Centralized validation service to handle reusable validation logic across multiple modules.
 * This includes Beneficiary, Quotation, User, Payment, and Policy validations.
 * <p>
 * Future scalability: add more module-specific validations here as needed.
 */
@Service
public class ValidationServiceImpl {
    private static final int MAX_TOTAL_SHARE = 100;

    /**
     * Ensure that the id field is not null for a beneficiary DTO.
     *
     * @param beneficiaryDto the Beneficiary DTO to validate
     * @throws WebException if id is null
     */
    public void validateBeneficiaryIdNotNull(BeneficiaryDto beneficiaryDto, String action) {
        if (beneficiaryDto.getId() == null) {
            throw new WebException(action + " Action requires an ID.");
        }
    }

    /**
     * Ensure that the action field is not null for a beneficiary DTO.
     *
     * @param beneficiaryDto the Beneficiary DTO to validate
     * @throws WebException if action is null
     */
    public void validateBeneficiaryActionNotNull(BeneficiaryDto beneficiaryDto) {
        if (beneficiaryDto.getAction() == null) {
            throw new WebException("Action cannot be null for beneficiary with ID: "
                    + beneficiaryDto.getId());
        }
    }

    /**
     * Validates that an object exists in a provided map by its ID.
     *
     * @param id          the ID to check
     * @param existingMap map containing existing entities
     * @param entityName  the name of the entity (for error message)
     * @throws WebException if the ID does not exist in the map
     */
    public <T> void validateEntityExists(Long id, Map<Long, T> existingMap, String entityName) {
        if (!existingMap.containsKey(id)) {
            throw new WebException(String.format("%s not found for ID: %s", entityName, id));
        }
    }

    /**
     * Validate that the share is positive for a beneficiary.
     *
     * @param beneficiaryDto the Beneficiary DTO to validate
     * @throws WebException if share is null or <= 0
     */
    public void validateBeneficiaryShare(BeneficiaryDto beneficiaryDto) {
        if (beneficiaryDto.getShare() == null || beneficiaryDto.getShare() <= 0) {
            throw new WebException("Share must be greater than 0 for beneficiary with ID: "
                    + beneficiaryDto.getId());
        }
    }

    public void validateMaxCount(int count) {
        if (count > MAX_BENEFICIARIES) {
            throw new WebException("Cannot exceed the maximum number of beneficiaries (" + MAX_BENEFICIARIES + ")");
        }
    }

    public void validateTotalShare(int totalShare) {
        if (totalShare > MAX_TOTAL_SHARE) {
            throw new WebException(
                    "Total share exceeds allowed maximum (" + MAX_TOTAL_SHARE + "%), current: " + totalShare + "%"
            );
        }
    }
}
