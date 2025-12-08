package com.insurance.policy.dto.response;

import com.insurance.policy.dto.PlanDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponseDto {
    private String referenceNumber;
    private String gender;
    private String dateOfBirth;
    private int ageNearestBirthday;
    private List<PlanDetailsDto> plans;
}