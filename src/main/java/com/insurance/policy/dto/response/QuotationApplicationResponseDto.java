package com.insurance.policy.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.insurance.policy.dto.PlanInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuotationApplicationResponseDto {
    private Long id;
    private String fullName;
    private String nric;
    private String gender;
    private String nationality;
    private String identificationNo;
    private String countryCode;
    private String countryOfBirth;
    private String phoneNo;
    private String email;
    private String title;
    private Integer age;
    private Date dateOfBirth;
    private boolean isSmoker;
    private Integer cigarettesNo;
    private String occupation;
    private String purposeOfTransaction;
    private String applicationStatus;
    // private Plan plan;
    private PlanInfoDto planResponseDto;
}