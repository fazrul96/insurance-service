package com.insurance.policy.dto.request;

import com.insurance.policy.util.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentRequestDto {
    @NotNull(message = "Quotation ID is required.")
    private Long quotationId;

    private Integer duration;

    @DecimalMin(value = "0.01", inclusive = true, message = "Payment amount must be greater than 0.")
    private BigDecimal paymentAmount;

    private PaymentStatus paymentStatus;
}
