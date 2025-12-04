package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.TERMS_DECLARATION)
public class TermsDeclaration extends BaseModel {
    @Id
    @Column(name = Columns.TERMS_ID)
    @JsonProperty(Columns.TERMS_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = Columns.TERMS_HTML, nullable = false, columnDefinition = "TEXT")
    private String termsHtml;

    @Column(name = Columns.STATUS)
    @JsonProperty(Columns.STATUS)
    private String status;

    @Column(name = Columns.CREATED_AT)
    @JsonProperty(Columns.CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = Columns.UPDATED_AT)
    @JsonProperty(Columns.UPDATED_AT)
    private LocalDateTime updatedAt;

    @Column(name = Columns.IS_REQUIRED)
    @JsonProperty(Columns.IS_REQUIRED)
    private Boolean isRequired;
}