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
@Table(name = Tables.CLAIM_DOCUMENT)
public class ClaimDocument extends BaseModel {
    @Id
    @Column(name = Columns.CLAIM_DOCUMENT_ID)
    @JsonProperty(Columns.CLAIM_DOCUMENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long claimDocumentId;

    @ManyToOne
    @JoinColumn(name = Columns.CLAIM_ID)
    private Claim claim;

    @ManyToOne
    @JoinColumn(name = Columns.DOCUMENT_TYPE_ID)
    private DocumentType documentType;

    @Column(name = Columns.CLAIM_DOCUMENT_URL)
    @JsonProperty(Columns.CLAIM_DOCUMENT_URL)
    private String documentUrl;

    @Column(name = Columns.CLAIM_DOCUMENT_UPLOAD)
    @JsonProperty(Columns.CLAIM_DOCUMENT_UPLOAD)
    private LocalDate documentUpload;
}