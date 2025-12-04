package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
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
    private String status; // ACTIVE / CANCELLED / EXPIRED

    @ManyToOne
    @JoinColumn(name = Columns.PLAN_ID)
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = Columns.USER_ID)
    private User user;

    @OneToOne
    @JoinColumn(name = Columns.PAYMENT_ID)
    @JsonIgnore
    private Payment payment;

    @OneToOne
    @JoinColumn(name = Columns.QUOTATION_ID, referencedColumnName = Columns.QUOTATION_ID, unique = true)
    @JsonManagedReference
    private QuotationApplication quotationApplication;
}