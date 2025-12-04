package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.RULE_SET)
public class RuleSet extends BaseModel {
    @Id
    @Column(name = Columns.RULESET_ID)
    @JsonProperty(Columns.RULESET_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = Columns.PLAN_ID)
    private Plan plan;

    @Column(name = Columns.RULE_TYPE)
    @JsonProperty(Columns.RULE_TYPE)
    private String ruleType;

    @Column(name = Columns.OPERATOR)
    @JsonProperty(Columns.OPERATOR)
    private String operator;

    @Column(name = Columns.AGE_LIMIT)
    @JsonProperty(Columns.AGE_LIMIT)
    private Integer ageLimit;

    @Column(name = Columns.COVERAGE_AMOUNT)
    @JsonProperty(Columns.COVERAGE_AMOUNT)
    private Integer coverageAmount;

    @Column(name = Columns.PREMIUM_AMOUNT)
    @JsonProperty(Columns.PREMIUM_AMOUNT)
    private Double premiumAmount;

    @Column(name = Columns.GENDER)
    @JsonProperty(Columns.GENDER)
    private String gender;

    @Column(name = Columns.PAYMENT_FREQUENCY)
    @JsonProperty(Columns.PAYMENT_FREQUENCY)
    private String paymentFrequency;

    @Column(name = Columns.STATUS)
    @JsonProperty(Columns.STATUS)
    private String status;
}