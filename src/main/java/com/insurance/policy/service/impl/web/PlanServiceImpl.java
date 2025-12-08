package com.insurance.policy.service.impl.web;

import com.insurance.policy.data.entity.Plan;
import com.insurance.policy.data.entity.RuleSet;
import com.insurance.policy.data.repository.PlanRepository;
import com.insurance.policy.data.repository.RuleSetRepository;
import com.insurance.policy.dto.PlanDetailsDto;
import com.insurance.policy.dto.request.PlanRequestDto;
import com.insurance.policy.dto.response.PlanResponseDto;
import com.insurance.policy.exception.WebException;
import com.insurance.policy.service.PlanService;
import com.insurance.policy.util.enums.GenderEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.insurance.policy.util.common.StringUtils.generateReferenceNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final RuleSetRepository ruleSetRepository;

    public Plan getPlan(String requestId, Long id) throws WebException {
        log.info("[RequestId: {}] Execute PlanServiceImpl.getPlan()", requestId);
        return planRepository.findById(id)
                .orElseThrow(() -> new WebException("Plan not found"));
    }

    public PlanResponseDto generatePlan(PlanRequestDto request, String requestId) {
        log.info("[RequestId: {}] Execute PlanServiceImpl.generatePlan()", requestId);

        int age = calculateNearestAge(request.getDateOfBirth());
        GenderEnum gender = request.getGender();
        String genderCode = gender.getCode();

        List<Plan> plans = planRepository.findAll();
        List<PlanDetailsDto> planDetails = new ArrayList<>();

        for (Plan plan : plans) {
            Double monthlyPremium = findPremium(plan.getId(), genderCode, "MONTHLY", age);
            Double yearlyPremium = findPremium(plan.getId(), genderCode, "YEARLY", age);

            if (monthlyPremium == null || yearlyPremium == null) {
                return null;
            }

            planDetails.add(mapPlanDetails(plan, monthlyPremium, yearlyPremium));
        }

        return mapPlanResponse(gender, request.getDateOfBirth(), age, planDetails);
    }

    private PlanDetailsDto mapPlanDetails(Plan plan, Double monthlyPremium, Double yearlyPremium) {
        PlanDetailsDto planDetails = new PlanDetailsDto();
        planDetails.setId(plan.getId());
        planDetails.setPlanName(plan.getPlanName());
        planDetails.setSumAssured(plan.getCoverageAmount());
        planDetails.setMonthlyPremium(monthlyPremium);
        planDetails.setYearlyPremium(yearlyPremium);
        planDetails.setCoverageTerm(plan.getDuration() + " years");

        return planDetails;
    }

    private PlanResponseDto mapPlanResponse(GenderEnum gender, LocalDate dob, int age, List<PlanDetailsDto> planDetails) {
        PlanResponseDto response = new PlanResponseDto();
        response.setReferenceNumber(generateReferenceNumber());
        response.setGender(gender.name().toLowerCase());
        response.setDateOfBirth(dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        response.setAgeNearestBirthday(age);
        response.setPlans(planDetails);

        return response;
    }

    private int calculateNearestAge(LocalDate dob) {
        LocalDate today = LocalDate.now();
        int age = Period.between(dob, today).getYears();

        // Nearest birthday rule
        if (today.getDayOfYear() < dob.withYear(today.getYear()).getDayOfYear()) {
            age += 1;
        }
        return age;
    }

    private Double findPremium(Long planId, String gender, String frequency, int age) {
        return ruleSetRepository
                .findMatchingRuleSet(planId, gender, frequency, age)
                .map(RuleSet::getPremiumAmount)
                .orElse(null);
    }
}