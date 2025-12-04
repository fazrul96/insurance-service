package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.PLAN)
public class Plan extends BaseModel {
    @Id
    @Column(name = Columns.PLAN_ID)
    @JsonProperty(Columns.PLAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Columns.PLAN_NAME)
    @JsonProperty(Columns.PLAN_NAME)
    private String planName;

    @Column(name = Columns.COVERAGE_AMOUNT)
    @JsonProperty(Columns.COVERAGE_AMOUNT)
    private Double coverageAmount;

    @Column(name = Columns.BASE_PREMIUM)
    @JsonProperty(Columns.BASE_PREMIUM)
    private BigDecimal basePremium;

    @Column(name = Columns.DURATION)
    @JsonProperty(Columns.DURATION)
    private Integer duration;

    @Column(name = Columns.STATUS)
    @JsonProperty(Columns.STATUS)
    private String status;
}