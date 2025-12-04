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
@Table(name = Tables.CLAIM_TYPE)
public class ClaimType extends BaseModel {
    @Id
    @Column(name = Columns.CLAIM_TYPE_ID)
    @JsonProperty(Columns.CLAIM_TYPE_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long claimTypeId;

    @Column(name = Columns.CLAIM_TYPE_NAME)
    @JsonProperty(Columns.CLAIM_TYPE_NAME)
    private String claimTypeName;

    @Column(name = Columns.CLAIM_TYPE_DESCRIPTION)
    @JsonProperty(Columns.CLAIM_TYPE_DESCRIPTION)
    private String claimTypeDescription;
}