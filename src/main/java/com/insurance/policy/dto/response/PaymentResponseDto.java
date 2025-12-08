package com.insurance.policy.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.insurance.policy.constants.GeneralConstant.STATUS;
import com.insurance.policy.dto.PaymentDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponseDto {
    private String message;
    private String status;
    private PolicyResponseDto policy;
    private PaymentDetailsDto paymentDetails;

    /**
     * Factory method to create a successful payment response.
     *
     * @param policyResponse the created or updated policy details after a successful payment
     * @param paymentDetails additional details related to the payment transaction
     * @return PaymentResponseDto representing a successful payment operation
     */
    public static PaymentResponseDto success(
            PolicyResponseDto policyResponse, PaymentDetailsDto paymentDetails
    ) {
        return PaymentResponseDto.builder()
                .policy(policyResponse)
                .paymentDetails(paymentDetails)
                .message("Payment processed successfully.")
                .status(STATUS.SUCCESS)
                .build();
    }

    /**
     * Factory method to create a failed payment response.
     *
     * @param reason a human-readable description of the failure
     * @return PaymentResponseDto representing a failed payment operation
     */
    public static PaymentResponseDto failure(String reason) {
        return PaymentResponseDto.builder()
                .message(reason)
                .status(STATUS.FAILED)
                .build();
    }
}