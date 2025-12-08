package com.insurance.policy.dto.response;

import com.insurance.policy.data.entity.TermsDeclaration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermsDeclarationResponseDto {
    @Builder.Default
    private List<TermsDeclaration> terms = new ArrayList<>();
}