package com.insurance.policy.service.impl.web;

import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.data.entity.Plan;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.data.entity.User;
import com.insurance.policy.data.repository.QuotationApplicationRepository;
import com.insurance.policy.dto.PersonDto;
import com.insurance.policy.dto.PlanInfoDto;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationSummaryResponseDto;
import com.insurance.policy.exception.WebException;
import com.insurance.policy.service.QuotationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuotationApplicationServiceImpl implements QuotationApplicationService {
    private final QuotationApplicationRepository quotationApplicationRepository;
    private final PlanServiceImpl planService;
    private final UserServiceImpl userService;

    @Override
    public QuotationApplicationSummaryResponseDto getAllQuotations(String requestId) {
        log.info("[RequestId: {}] Execute QuotationApplicationServiceImpl.getAllQuotations()", requestId);

        List<QuotationApplicationResponseDto> response = quotationApplicationRepository.findAll()
                .stream()
                .map(this::toQuotationApplicationResponse)
                .toList();

        return new QuotationApplicationSummaryResponseDto(response);
    }

    @Override
    public QuotationApplication getQuotationsById(String requestId, Long id) {
        log.info("[RequestId: {}] Execute QuotationApplicationServiceImpl.getQuotationsById()", requestId);
        return quotationApplicationRepository.findById(id)
                .orElseThrow(() -> new WebException("Quotation not found"));
    }

    @Override
    public QuotationApplicationSummaryResponseDto getQuotationsByStatus(String requestId, String applicationStatus) {
        log.info("[RequestId: {}] Execute QuotationApplicationServiceImpl.getQuotationsByStatus()", requestId);

        List<QuotationApplicationResponseDto> response = quotationApplicationRepository
                .findByApplicationStatus(applicationStatus)
                .stream()
                .map(this::toQuotationApplicationResponse)
                .toList();

        return new QuotationApplicationSummaryResponseDto(response);
    }

    @Override
    public QuotationApplicationSummaryResponseDto getQuotationsByUserId(String requestId, String userId) {
        log.info("[RequestId: {}] Execute QuotationApplicationServiceImpl.getQuotationsByUserId()", requestId);

        List<QuotationApplicationResponseDto> response = quotationApplicationRepository.findByUserId(userId)
                .stream()
                .map(this::toQuotationApplicationResponse)
                .toList();

        return new QuotationApplicationSummaryResponseDto(response);
    }

    @Override
    public QuotationApplication createQuotation(QuotationApplication application) {
        return quotationApplicationRepository.save(application);
    }

    @Override
    public QuotationApplicationResponseDto processQuotation(
            String requestId, String userId, QuotationApplicationRequestDto request) {
        log.info("[RequestId: {}] Execute QuotationApplicationServiceImpl.processQuotation()", requestId);

        PersonDto person = request.getPersonDto();
        PlanInfoDto planInfo = request.getPlanInfoDto();
        User user = userService.getUserByUserId(requestId, userId);
        Plan plan = planService.getPlan(requestId, planInfo.getId());

        QuotationApplication application = toQuotationApplication(person, planInfo, user, plan);
        return toQuotationApplicationResponse(createQuotation(application));
    }

    private QuotationApplication toQuotationApplication(
            PersonDto personDto, PlanInfoDto planInfoDto, User user, Plan plan) {
        return QuotationApplication.builder()
                .fullName(personDto.getFullName())
                .gender(personDto.getGender())
                .nationality(personDto.getNationality())
                .identificationNo(personDto.getIdentificationNo())
                .otherId(personDto.getOtherId())
                .countryOfBirth(personDto.getCountryOfBirth())
                .age(personDto.getAge())
                .title(personDto.getTitle())
                .countryCode(personDto.getCountryCode())
                .phoneNo(personDto.getPhoneNo())
                .email(personDto.getEmail())
                .dateOfBirth(personDto.getDateOfBirth())
                .isSmoker(personDto.isSmoker())
                .isUsPerson(Boolean.TRUE.equals(personDto.getIsUsPerson()))
                .cigarettesNo(personDto.getCigarettesNo())
                .occupation(personDto.getOccupation())
                .purposeOfTransaction(personDto.getPurposeOfTransaction())
                .premiumAmount(planInfoDto.getPremiumAmount())
                .premiumMode(planInfoDto.getPremiumMode())
                .referenceNumber(planInfoDto.getReferenceNumber())
                .plan(plan)
                .user(user)
                .applicationStatus(GeneralConstant.STATUS.PENDING)
                .build();
    }

    @Override
    public QuotationApplicationResponseDto toQuotationApplicationResponse(QuotationApplication request) {
        return QuotationApplicationResponseDto.builder()
                .id(request.getId())
                .applicationStatus(request.getApplicationStatus())
                .cigarettesNo(request.getCigarettesNo())
                .countryOfBirth(request.getCountryOfBirth())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .gender(request.getGender())
                .identificationNo(request.getIdentificationNo())
                .isSmoker(request.isSmoker())
                .nationality(request.getNationality())
                .occupation(request.getOccupation())
                .phoneNo(request.getPhoneNo())
                .purposeOfTransaction(request.getPurposeOfTransaction())
                .plan(request.getPlan())
                .build();
    }
}