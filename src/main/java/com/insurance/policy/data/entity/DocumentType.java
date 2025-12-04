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
@Table(name = Tables.DOCUMENT_TYPE)
public class DocumentType extends BaseModel {
    @Id
    @Column(name = Columns.DOCUMENT_TYPE_ID)
    @JsonProperty(Columns.DOCUMENT_TYPE_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long documentTypeId;

    @Column(name = Columns.DOCUMENT_TYPE_NAME)
    @JsonProperty(Columns.DOCUMENT_TYPE_NAME)
    private String documentTypeName;

    @ManyToOne
    @JoinColumn(name = Columns.CLAIM_TYPE_ID)
    private ClaimType claimType;

    @Column(name = Columns.DOCUMENT_TYPE_IS_REQUIRED)
    @JsonProperty(Columns.DOCUMENT_TYPE_IS_REQUIRED)
    private boolean isRequired;
}