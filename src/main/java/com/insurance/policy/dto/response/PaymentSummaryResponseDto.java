package com.insurance.policy.dto.response;

import com.insurance.policy.dto.PaymentDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSummaryResponseDto {
    private List<PaymentDetailsDto> payments;
}