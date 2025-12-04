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
@Table(name = Tables.BENEFICIARY)
public class Beneficiary extends BaseModel {
    @Id
    @Column(name = Columns.BENEFICIARY_ID)
    @JsonProperty(Columns.BENEFICIARY_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = Columns.POLICY_ID, referencedColumnName = Columns.POLICY_ID)
    private Policy policy;

    @Column(name = Columns.BENEFICIARY_NAME)
    @JsonProperty(Columns.BENEFICIARY_NAME)
    private String beneficiaryName;

    @Column(name = Columns.RELATIONSHIP_TO_INSURED)
    @JsonProperty(Columns.RELATIONSHIP_TO_INSURED)
    private String relationshipToInsured;

    @Column(name = Columns.SHARE)
    @JsonProperty(Columns.SHARE)
    private Float share;
}