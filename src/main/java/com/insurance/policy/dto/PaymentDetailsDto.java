package com.insurance.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentDetailsDto {
    private Long paymentId;
    private Date paymentDate;
    private BigDecimal paymentAmount;
    private String paymentStatus;
    private String paymentReferenceNumber;
}