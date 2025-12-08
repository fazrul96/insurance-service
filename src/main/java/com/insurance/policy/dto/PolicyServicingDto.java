package com.insurance.policy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyServicingDto {
    private String title;
    private String fullName;
    private String countryCode;
    private String phoneNo;
    private String email;
}