package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Tables.PAYMENT)
public class Payment extends BaseModel {
    @Id
    @Column(name = Columns.PAYMENT_ID)
    @JsonProperty(Columns.PAYMENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Columns.PAYMENT_DATE)
    @JsonProperty(Columns.PAYMENT_DATE)
    private Date paymentDate;

    @Column(name = Columns.PAYMENT_AMOUNT)
    @JsonProperty(Columns.PAYMENT_AMOUNT)
    private BigDecimal paymentAmount;

    @Column(name = Columns.PAYMENT_STATUS)
    @JsonProperty(Columns.PAYMENT_STATUS)
    private String paymentStatus;

    @Column(name = Columns.DURATION)
    @JsonProperty(Columns.DURATION)
    private Integer duration;

    @Column(name = Columns.REFERENCE_NUMBER)
    @JsonProperty(Columns.REFERENCE_NUMBER)
    private String referenceNumber;

    @Column(name = Columns.QUOTATION_APPLICATION_ID)
    private Long quotationApplication;

//    @OneToOne
//    @JoinColumn(name = Columns.QUOTATION_APPLICATION_ID, referencedColumnName = Columns.QUOTATION_ID, unique = true)
//    @JsonIgnore
//    private QuotationApplication quotationApplication;
}
