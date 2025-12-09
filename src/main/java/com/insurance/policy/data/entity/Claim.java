package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Tables.CLAIM)
public class Claim extends BaseModel {
    @Id
    @Column(name = Columns.CLAIM_ID)
    @JsonProperty(Columns.CLAIM_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = Columns.USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = Columns.CLAIM_TYPE_ID)
    private ClaimType claimType;

    @ManyToOne
    @JoinColumn(name = Columns.POLICY_ID)
    private Policy policy;

    @Column(name = Columns.CLAIM_DATE)
    @JsonProperty(Columns.CLAIM_DATE)
    private LocalDate claimDate;

    @Column(name = Columns.CLAIM_STATUS)
    @JsonProperty(Columns.CLAIM_STATUS)
    private String claimStatus;
}