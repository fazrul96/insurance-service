package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Tables.POLICY)
public class Policy extends BaseModel {
    @Id
    @Column(name = Columns.POLICY_ID)
    @JsonProperty(Columns.POLICY_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Columns.POLICY_NO)
    @JsonProperty(Columns.POLICY_NO)
    private String policyNo;

    @Column(name = Columns.START_DATE)
    @JsonProperty(Columns.START_DATE)
    private Date startDate;

    @Column(name = Columns.END_DATE)
    @JsonProperty(Columns.END_DATE)
    private Date endDate;

    @Column(name = Columns.STATUS)
    @JsonProperty(Columns.STATUS)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PLAN_ID, referencedColumnName = Columns.PLAN_ID)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.USER_ID)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PAYMENT_ID)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.QUOTATION_ID, referencedColumnName = Columns.QUOTATION_ID, unique = true)
    @JsonManagedReference
    private QuotationApplication quotationApplication;
}